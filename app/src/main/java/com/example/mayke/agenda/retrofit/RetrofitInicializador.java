package com.example.mayke.agenda.retrofit;

import com.example.mayke.agenda.retrofit.retrofit_services.IAlunoService;
import com.example.mayke.agenda.retrofit.retrofit_services.IDispositivoService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by mayke on 17/01/2018.
 */

public class RetrofitInicializador {

    private  final Retrofit retrofit;

    //configuração do retrofit

    public RetrofitInicializador() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        this.retrofit = new Retrofit
                            .Builder()
                            .baseUrl("http://ip da máquina:8080/api/")
                            .addConverterFactory(JacksonConverterFactory.create())
                            .client(client.build())
                            .build();
    }

    public IAlunoService getAlunoService() {
        return retrofit.create(IAlunoService.class);
    }

    public IDispositivoService getDispositivoService() {
        return retrofit.create(IDispositivoService.class);
    }
}
