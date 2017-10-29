package com.example.mayke.agenda;

import android.widget.EditText;
import android.widget.RatingBar;

import com.example.mayke.agenda.modelo.Aluno;

/**
 * Created by mayke on 27/10/2017.
 */

public class Formulario {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoRatingAluno;

    private Aluno aluno;

    public Formulario(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formularioNome);
        campoEndereco = (EditText) activity.findViewById(R.id.formularioEndereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formularioTelefone);
        campoSite = (EditText) activity.findViewById(R.id.formularioSite);
        campoRatingAluno = (RatingBar) activity.findViewById(R.id.formularioNota);
        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf((campoRatingAluno.getProgress())));

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoRatingAluno.setProgress(aluno.getNota().intValue());
        this.aluno = aluno;
    }
}
