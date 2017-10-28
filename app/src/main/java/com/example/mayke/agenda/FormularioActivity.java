package com.example.mayke.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;

import java.util.zip.Inflater;

public class FormularioActivity extends AppCompatActivity {

    private Formulario formularioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        formularioHelper = new Formulario(this);

    }

    //método que define quais itens ficam na action bar do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate transforma o xml do menu em view
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //método que é chamado cada vez q um item do menu é clicado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DecideAcaoDeAcordoComItemDoMenu(item);

        return super.onOptionsItemSelected(item);
    }

    private void DecideAcaoDeAcordoComItemDoMenu(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = formularioHelper.getAluno();
                AlunoDAO alunoDAO = new AlunoDAO(this);
                alunoDAO.insere(aluno);

                Toast.makeText(FormularioActivity.this, "Aluno ".concat(aluno.getNome()).concat(" foi salvo!"), Toast.LENGTH_SHORT).show();

                //mata a atual activity e volta para a anterior
                finish();
                break;
        }
    }
}
