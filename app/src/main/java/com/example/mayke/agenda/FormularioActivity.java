package com.example.mayke.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;

import java.io.File;
import java.util.zip.Inflater;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private Formulario formularioHelper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //funciona como um view model, passando os dados do modelo para a view
        formularioHelper = new Formulario(this);

        //recupera o objeto passado pela outra activity
        Intent intent = getIntent();
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");

        if (aluno != null){
            formularioHelper.preencheFormulario(aluno);
        }

        clickBtnFoto();
    }

    private void clickBtnFoto() {
        Button btnFoto = findViewById(R.id.formularioBtnFoto);
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });
    }

    //Esse método é chamado após o startActivityForResult, antes de iniciar o ciclo de vida das activities
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) { //para o caso do candango resolver cancelar a ação de tirar a foto
            if (requestCode == CODIGO_CAMERA) {
                ImageView foto = (ImageView) findViewById(R.id.formularioFoto);
                Bitmap bitmapFoto = BitmapFactory.decodeFile(caminhoFoto);
                Bitmap bitmapFotoReduzido = Bitmap.createScaledBitmap(bitmapFoto, 300, 300, true);
                foto.setImageBitmap(bitmapFotoReduzido);
                foto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }

    //método que define quais itens ficam na action bar do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate transforma o xml do menu em view
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //método que é chamado cada vez q um item do menu é clicado
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DecideAcaoDeAcordoComItemDoMenu(item);

        return super.onOptionsItemSelected(item);
    }

    private void DecideAcaoDeAcordoComItemDoMenu(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_formulario_ok:

                Aluno aluno = formularioHelper.getAluno();

                AlunoDAO alunoDAO = new AlunoDAO(this);

                if (aluno.getId() != null){
                    alunoDAO.altera(aluno);
                } else {
                    alunoDAO.insere(aluno);
                }

                Toast.makeText(FormularioActivity.this, "Aluno ".concat(aluno.getNome()).concat(" foi salvo!"), Toast.LENGTH_SHORT).show();

                //mata a atual activity e volta para a anterior
                finish();
                break;
        }
    }
}
