package com.example.mayke.agenda.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.mayke.agenda.FormularioActivity;
import com.example.mayke.agenda.R;
import com.example.mayke.agenda.modelo.Aluno;

/**
 * Created by mayke on 27/10/2017.
 */

public class FormularioHelper {
    private final EditText campoNome;
    private final EditText campoEndereco;
    private final EditText campoTelefone;
    private final EditText campoSite;
    private final RatingBar campoRatingAluno;
    private final ImageView campoFoto;

    private Aluno aluno;

    public FormularioHelper(FormularioActivity activity){
        campoNome = (EditText) activity.findViewById(R.id.formularioNome);
        campoEndereco = (EditText) activity.findViewById(R.id.formularioEndereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formularioTelefone);
        campoSite = (EditText) activity.findViewById(R.id.formularioSite);
        campoRatingAluno = (RatingBar) activity.findViewById(R.id.formularioNota);
        campoFoto = (ImageView) activity.findViewById(R.id.formularioFoto);
        aluno = new Aluno();
    }

    public Aluno getAluno() {
        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf((campoRatingAluno.getProgress())));
        aluno.setCaminhoFoto((String) campoFoto.getTag());

        return aluno;
    }

    public void preencheFormulario(Aluno aluno) {
        campoNome.setText(aluno.getNome());
        campoEndereco.setText(aluno.getEndereco());
        campoTelefone.setText(aluno.getTelefone());
        campoSite.setText(aluno.getSite());
        campoRatingAluno.setProgress(aluno.getNota().intValue());
        carregaImagem(aluno.getCaminhoFoto());

        this.aluno = aluno;
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            //tratamento de adicionar a foto ao ImageView
            Bitmap bitmapFoto = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapFotoReduzido = Bitmap.createScaledBitmap(bitmapFoto, 300, 300, true);
            campoFoto.setImageBitmap(bitmapFotoReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            campoFoto.setTag(caminhoFoto); //associa o caminho da foto com o objeto de ImageView
        }
    }
}
