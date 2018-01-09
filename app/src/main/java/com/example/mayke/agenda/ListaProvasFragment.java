package com.example.mayke.agenda;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mayke.agenda.modelo.Prova;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mayke on 09/01/2018.
 */

/*SEMPRE QUE IMPORTAR CLASSE FRAGMENT, IMPORTAR DA BIBLIOTECA DE SUPORTE.
 * SE NÃO, SÓ FUNCIONA DA API 23 PRA FRENTE
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicosPort = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Prova provaPortugues = new Prova("Portugues", "25/05¹2016", topicosPort);

        List<String> topicosMat = Arrays.asList("Equações", "trigonometria");
        Prova provaMatematica = new Prova("Matematica", "27/05¹2016", topicosMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        final ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), "Clicou na prova de ".concat(prova.getMateria()), Toast.LENGTH_SHORT).show();
                Intent vaiParaDetalhes = new Intent(getContext(), DetalhesProvaActivity.class);
                vaiParaDetalhes.putExtra("prova", prova);

                startActivity(vaiParaDetalhes);
            }
        });

        return view;
    }
}


















