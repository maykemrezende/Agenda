package com.example.mayke.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.mayke.agenda.converter.AlunoConverter;
import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by mayke on 02/11/2017.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {
    //os generics entre os sinais de maior e menor são parâmetros do doInBackGround, progress e retorno

    private Context context;
    private ProgressDialog dialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();

        AlunoConverter conversorAluno = new AlunoConverter();
        String alunosJSON = conversorAluno.converterParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(alunosJSON);

        return resposta;
    }

    // onPostExecute é executado depois da execução, passando o return pra onPostExecute, do doInBackground e na thread principal
    // doInBackground é executado em uma thread secundária
    @Override
    protected void onPostExecute(String resposta) {
        dialog.dismiss();
        Toast.makeText(context, resposta, Toast.LENGTH_SHORT).show();
    }

    // onPreExecute é executado antes da execução, passando o return pra onPreExecute, do doInBackground e na thread principal
    // doInBackground é executado em uma thread secundária
    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,"Aguarde", "Enviando alunos...", true, true);
    }
}
