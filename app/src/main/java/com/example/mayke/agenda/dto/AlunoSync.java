package com.example.mayke.agenda.dto;

import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by mayke on 19/01/2018.
 */

public class AlunoSync {


    private List<Aluno> alunos;
    private String momentoDaUltimaModificacao;

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public String getMomentoDaUltimaModificacao() {
        return momentoDaUltimaModificacao;
    }
}
