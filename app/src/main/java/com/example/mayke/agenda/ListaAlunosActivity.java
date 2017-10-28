package com.example.mayke.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

public class ListaAlunosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);

        BtnNovoAlunoClick();

    }

    @Override
    protected void onResume() {
        CarregaLista();

        super.onResume();
    }

    private void CarregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscaAlunos();

        //findviewbyid traz uma referência de um item do xml de layout a partir de um id
        ListView listaAlunos = (ListView) super.findViewById(R.id.lista_alunos);

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
