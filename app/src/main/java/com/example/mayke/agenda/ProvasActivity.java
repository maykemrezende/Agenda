package com.example.mayke.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mayke.agenda.fragment.DetalhesProvaFragment;
import com.example.mayke.agenda.fragment.ListaProvasFragment;
import com.example.mayke.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal, new ListaProvasFragment());

        if(estaNoModoPaisagem()){
            tx.replace(R.id.frame_secundario, new DetalhesProvaFragment());
        }

        tx.commit();
    }

    private boolean estaNoModoPaisagem() {
        //oram criados dois arquivos bools.xml na pasta values para definir se está no modo paisagem ou não
        return getResources().getBoolean(R.bool.modoPaisagem);
    }

    public void selecionaProva(Prova prova) {
        //no modo paisagem, frame principal é o da direita e o secundário, o da esquerda
        //ambos são fragments

        FragmentManager manager = getSupportFragmentManager();

        if (!estaNoModoPaisagem()) {
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesProvaFragment = new DetalhesProvaFragment();
            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);

            detalhesProvaFragment.setArguments(parametros);

            tx.replace(R.id.frame_principal, detalhesProvaFragment);
            tx.addToBackStack(null); //null marca o identificador do fragment na pilha para voltar pra ele em algum momento

            tx.commit();
        } else {
            DetalhesProvaFragment detalhesFragment =
                    (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_secundario);

            detalhesFragment.PopulaCamposCom(prova);
        }
    }
}
