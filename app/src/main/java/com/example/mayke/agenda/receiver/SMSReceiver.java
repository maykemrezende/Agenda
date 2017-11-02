package com.example.mayke.agenda.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.example.mayke.agenda.ListaAlunosActivity;

/**
 * Created by mayke on 02/11/2017.
 */

public class SMSReceiver extends BroadcastReceiver {
    // Broadcast receivers enable applications to receive intents that are broadcast by the system or by other applications,
    // even when other components of the application are not running.
    // Quando o app de sms do fabricante recebe um sms, ele envia uma msg para todos os apps do celular perguntando quem está interessado em tratá-lo
    // Criar uma tag receiver no manifest sempre que criar uma classe receiver

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Chegou um SMS!", Toast.LENGTH_SHORT).show();
    }
}
