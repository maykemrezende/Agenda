package com.example.mayke.agenda.receiver;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.example.mayke.agenda.ListaAlunosActivity;
import com.example.mayke.agenda.R;
import com.example.mayke.agenda.dao.AlunoDAO;

import static android.telephony.SmsMessage.createFromPdu;

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
        //pdu é uma string de bytes que representa um sms

        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");

        SmsMessage sms = createFromPdu(pdu, formato);
        String telefone = sms.getDisplayOriginatingAddress();
        AlunoDAO dao = new AlunoDAO(context);

        if (dao.ehAluno(telefone)){
            Toast.makeText(context, "Chegou um SMS do aluno ".concat(telefone), Toast.LENGTH_SHORT).show();
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
        }
    }
}
