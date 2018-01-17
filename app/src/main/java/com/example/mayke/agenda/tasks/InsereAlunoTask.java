package com.example.mayke.agenda.tasks;

import android.os.AsyncTask;

import com.example.mayke.agenda.WebClient;
import com.example.mayke.agenda.converter.AlunoConverter;
import com.example.mayke.agenda.modelo.Aluno;

/**
 * Created by mayke on 17/01/2018.
 */

public class InsereAlunoTask extends AsyncTask{
    private final Aluno aluno;

    public InsereAlunoTask(Aluno aluno) {

        this.aluno = aluno;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        /*String alunoJson = new AlunoConverter().converterParaJSONCompleto(aluno);
        new WebClient().insereAluno(alunoJson);*/
        return null;

    }
}
