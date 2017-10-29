package com.example.mayke.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        listaAlunos = (ListView) super.findViewById(R.id.lista_alunos);

        alteraAluno(listaAlunos);

        BtnNovoAlunoClick();

        //primeiro registra para o menu de contexto antes de criar o menu de contexto. listaAlunos é quem ativa o menu de contexto
        registerForContextMenu(listaAlunos);

    }

    private void alteraAluno(final ListView listaAlunos) {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long idItem) {
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(position);

                Intent intentVaiProFormAlteracao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);

                //envia os dados para outra activity, pra enviar o objeto a classe precisa ter "implements serializable"
                intentVaiProFormAlteracao.putExtra("aluno", aluno);
                startActivity(intentVaiProFormAlteracao);
            }
        });
    }

    @Override
    protected void onResume() {
        CarregaLista();

        super.onResume();
    }

    //menu de contexto é aquele que clica e segura em algum item
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        //cria o item do menu dessa forma, em vez de criar pelo xml
        MenuItem excluirAluno = menu.add("Excluir");
        excluirAluno.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
                //devolve o aluno na posição segurada do menu de contexto
                Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

                AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
                dao.deleta(aluno);

                CarregaLista();

                return false;
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);
    }

    private void CarregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscaAlunos();

        //findviewbyid traz uma referência de um item do xml de layout a partir de um id

        //Adapter para adicionar o array e usá-lo na view, pq ele converte de string para view
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
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
}