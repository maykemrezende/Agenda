package com.example.mayke.agenda.retrofit.retrofit_services;

import com.example.mayke.agenda.dto.AlunoSync;
import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by mayke on 17/01/2018.
 */

public interface IAlunoService {

    @POST("aluno")
    Call<Void> insereAluno(@Body Aluno aluno);

    @GET("aluno")
    Call<AlunoSync> listaAlunos();

    @DELETE("aluno/{id}")
    Call<Void> deletaAluno(@Path("id") String id);
}
