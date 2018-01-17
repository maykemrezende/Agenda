package com.example.mayke.agenda.retrofit;

import com.example.mayke.agenda.retrofit.retrofit_services.IAlunoService;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by mayke on 17/01/2018.
 */

public class RetrofitInicializador {

    private  final Retrofit retrofit;

    //configuração do retrofit

    public RetrofitInicializador() {
       this.retrofit = new Retrofit
                            .Builder()
                            .baseUrl("ip da máquina:8080/api/")
                            .addConverterFactory(JacksonConverterFactory.create())
                            .build();
    }

    public IAlunoService getAlunoService() {
        return retrofit.create(IAlunoService.class);
    }
}
