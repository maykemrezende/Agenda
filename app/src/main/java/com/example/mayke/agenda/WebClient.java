package com.example.mayke.agenda;

import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by mayke on 02/11/2017.
 */

public class WebClient {

    private URL url;

    public String post(String json){
        String stringURL = "https://www.caelum.com.br/mobile";

        return conecta(json, stringURL);
    }

    public void insereAluno(String alunoJson) {
        String stringURL = "ip da maquina:8080/api/aluno";

        conecta(alunoJson, stringURL);
    }

    @Nullable
    private String conecta(String json, String stringURL) {
        try {
            url = new URL(stringURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true); //define que vai fazer um post

            PrintStream outPut = new PrintStream(connection.getOutputStream());
            outPut.println(json);

            connection.connect();

            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            return resposta;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
