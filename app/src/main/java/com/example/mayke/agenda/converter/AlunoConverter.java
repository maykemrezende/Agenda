package com.example.mayke.agenda.converter;

import com.example.mayke.agenda.modelo.Aluno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by mayke on 02/11/2017.
 */

public class AlunoConverter {
    public String converterParaJSON(List<Aluno> alunos) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("list").array().object().key("aluno").array();
            for (Aluno aluno : alunos){
                js.object();
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNota());
                js.endObject();
            }
            js.endArray().endObject().endArray().endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
