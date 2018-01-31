package com.example.mayke.agenda.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

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
    }
}
