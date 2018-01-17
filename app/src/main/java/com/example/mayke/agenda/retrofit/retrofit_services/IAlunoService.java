package com.example.mayke.agenda.retrofit.retrofit_services;

import com.example.mayke.agenda.modelo.Aluno;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mayke on 17/01/2018.
 */

public interface IAlunoService {

    @POST("aluno")
    Call<Void> insereAluno(@Body Aluno aluno);
}
