package com.example.mayke.agenda.retrofit.retrofit_services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by mayke on 30/01/2018.
 */

public interface IDispositivoService {

    @POST("firebase/dispositivo")
    Call<Void> enviaToken(@Header("token") String token);
}
