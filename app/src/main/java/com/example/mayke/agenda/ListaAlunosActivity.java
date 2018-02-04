package com.example.mayke.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mayke.agenda.adapter.AlunosAdapter;
import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.dto.AlunoSync;
import com.example.mayke.agenda.events.AtualizarListaAlunoEvent;
import com.example.mayke.agenda.modelo.Aluno;
import com.example.mayke.agenda.retrofit.RetrofitInicializador;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;
    private SwipeRefreshLayout swipeListaAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        EventBus eventBus = EventBus.getDefault();
        eventBus.register(this);

        listaAlunos = (ListView) super.findViewById(R.id.lista_alunos);

        //SwipeRefreshLayout é o ícone pra dar refresh na página e atualizar os dados da lista,
        //da mesma forma que há no facebook, quando puxa a tela pra baixo e aparece o ícone da seta circular girando
        //é preciso colocar o swiperefresh no xml da página onde ele será usado
        //é preciso colocar swipeListaAluno.setRefreshing(false); no callback do método chamado no onresponse, colocar tanto no sucesso qnt falha
        swipeListaAluno = (SwipeRefreshLayout) findViewById(R.id.swipe_lista_aluno);
        swipeListaAluno.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                CarregaListaAlunosServidor();
            }
        });

        alteraAluno(listaAlunos);

        BtnNovoAlunoClick();

        //primeiro registra para o menu de contexto antes de criar o menu de contexto. listaAlunos é quem ativa o menu de contexto
        //Menu de contexto é o que clica e segura em um item da lista
        registerForContextMenu(listaAlunos);

        //carrega a lista de alunos do servidor, pra poder habilitar a edição dos alunos.
        // No onResume, outra requisição pra pegar as informações do aluno era feita antes mesmo de alterar o aluno
        CarregaListaAlunosServidor();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void atualizaListaAlunoEvent(AtualizarListaAlunoEvent event){
        CarregaLista();
    }

    @Override
    protected void onResume() {
        CarregaLista();

        super.onResume();
    }

    private void CarregaListaAlunosServidor() {
        Call<AlunoSync> call = new RetrofitInicializador().getAlunoService().listaAlunos();
        call.enqueue(new Callback<AlunoSync>() {
            @Override
            public void onResponse(Call<AlunoSync> call, Response<AlunoSync> response) {
                AlunoSync alunoSync = response.body();//body devolve o generics que estamos esperando que o servidor devolva. Ou seja, nesse caso, a lista de alunos
                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                alunoDAO.SincronizaAlunosDoServidor(alunoSync.getAlunos());
                CarregaLista();
                swipeListaAluno.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<AlunoSync> call, Throwable t) {
                Log.e("onFailure chamado", t.getMessage());
                swipeListaAluno.setRefreshing(false);
            }
        });
    }

    //menu de contexto é aquele que clica e segura em algum item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //devolve o aluno na posição segurada do menu de contexto
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        //********************** LIGAÇÃO ************************************
        MenuItem itemLigacao = menu.add("Ligar");
        itemLigacao.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (ActivityCompat.checkSelfPermission(ListaAlunosActivity.this, Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //se não tiver permissão, pede ao usuário
                    // usar requestPermission sempre que quiser pedir uma permissão ao user
                    // request code serve pra diferenciar a permissão no método onRequestPermissionsResult, se é pra acessar ligação, enviar sms, acessar gps
                    ActivityCompat.requestPermissions(ListaAlunosActivity.this,
                            new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {

                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:".concat(aluno.getTelefone())));
                    startActivity(intentLigar);
                }
                return false;
            }
        });


        //*********************** VISUALIZAR MAPA ****************************
        MenuItem itemMapa = menu.add("Visualizar no mapa");
        Intent intentMapa = new Intent(Intent.ACTION_VIEW);
        intentMapa.setData(Uri.parse("geo:0,0?q=".concat(aluno.getEndereco())));
        itemMapa.setIntent(intentMapa);


        //*********************** ENVIAR SMS **********************************
        MenuItem itemSMS = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:".concat(aluno.getTelefone())));
        itemSMS.setIntent(intentSMS);

        //************************* VISITAR SITE *****************************

        //cria o item do menu dessa forma, em vez de criar pelo xml
        MenuItem itemSite = menu.add("Visitar site");

        //essa forma de intent é a intent explícita
        //Intent intentSite = new Intent(ListaAlunosActivity.this, Browser.class);

        //acessar site
        String site = aluno.getSite();
        if (!site.startsWith("http://") && (!site.startsWith("https://"))){
            StringBuilder siteString = new StringBuilder();
            siteString.append("http://");
            siteString.append(site);
            site = siteString.toString();
        }

        //Intent implícita
        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(site));
        //setIntent não deixa ser preciso criar um listener pra criar um intent
        itemSite.setIntent(intentSite);

        //******************************** EXCLUIR ALUNO ***************************************

        MenuItem excluirAluno = menu.add("Excluir");
        excluirAluno.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                Call<Void> call = new RetrofitInicializador().getAlunoService().deletaAluno(aluno.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                        dao.deleta(aluno);

                        CarregaLista();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(ListaAlunosActivity.this,
                                "Não foi possível excluir o aluno. Por favor, tente novamente.", Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_notas:
                new EnviaAlunosTask(this).execute();
                break;
            case R.id.menu_baixar_provas:
                Intent vaiParaProvas = new Intent(this, ProvasActivity.class);
                startActivity(vaiParaProvas);
                break;
            case R.id.menu_mapa:
                Intent vaiParaMapa = new Intent(this, MapaActivity.class);
                startActivity(vaiParaMapa);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    private void CarregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscaAlunos();

        for (Aluno aluno :
             alunos) {
            Log.i("id do aluno", String.valueOf(aluno.getId()));
        }

        //findviewbyid traz uma referência de um item do xml de layout a partir de um id

        //Adapter para transformar os dados de cada item do dataset em uma view e colocar no listview
        //Adapter antigo pegava um objeto e transformava em uma string usando um layout básico do android
        //Criado adapter específico para pegar os dados do modelo Aluno e criar a view para cada item desse modelo,
        //pq foi criado um layout específico (list_item.xml) para cada item da lista

        AlunosAdapter adapter = new AlunosAdapter(this, alunos);
        listaAlunos.setAdapter(adapter);
    }

    private void BtnNovoAlunoClick(){
        Button btnNovoAluno = (Button) super.findViewById(R.id.listaBtnNovoAluno);

        //cria listener do botão
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chama o formulario de novo aluno
                //Intent é "intenção", intenção de chamar a activity. Digo ao android pra qual activity quero ir
                Intent intentVaiProFormNovoAluno = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
                startActivity(intentVaiProFormNovoAluno);
            }
        });
    }

    private void alteraAluno(final ListView listaAlunos) {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long idItem) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormAlteracao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                //envia os dados para outra activity, pra enviar o objeto a classe precisa ter "implements serializable"
                //putExtra envia informações não obrigatórias
                intentVaiProFormAlteracao.putExtra("aluno", aluno);
                startActivity(intentVaiProFormAlteracao);
            }
        });
    }
}
