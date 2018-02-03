package com.example.mayke.agenda.firebase;

import android.util.Log;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.dto.AlunoSync;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.util.Map;

/**
 * Created by mayke on 30/01/2018.
 */

public class AgendaMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> mensagemDoServidor = remoteMessage.getData();
        Log.i("mensagem recebida", String.valueOf(mensagemDoServidor));

        converteParaAluno(mensagemDoServidor);
    }

    private void converteParaAluno(Map<String, String> mensagemDoServidor) {
        String chaveDeAcesso = "alunoSync";
        if(mensagemDoServidor.containsKey(chaveDeAcesso)){
            String json = mensagemDoServidor.get(chaveDeAcesso);
            ObjectMapper mapper = new ObjectMapper();
            try {
                AlunoSync alunoSync = mapper.readValue(json, AlunoSync.class);
                AlunoDAO alunoDAO = new AlunoDAO(this);
                alunoDAO.SincronizaAlunosDoServidor(alunoSync.getAlunos());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
