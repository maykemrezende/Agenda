package com.example.mayke.agenda.fragment;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.mayke.agenda.dao.AlunoDAO;
import com.example.mayke.agenda.modelo.Aluno;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by mayke on 16/01/2018.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng posicaoDaEscola = PegaCoordenadaDoEndereco("Rua Vergueiro 3185, Vila Mariana, Sao Paulo");

        if (posicaoDaEscola != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoDaEscola, 17);
            googleMap.moveCamera(update);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        for(Aluno aluno : alunoDAO.buscaAlunos()){
            LatLng coordenadaDoAluno = PegaCoordenadaDoEndereco(aluno.getEndereco());

            if (coordenadaDoAluno != null){
                MarkerOptions pininhoDoAluno = new MarkerOptions();
                pininhoDoAluno.position(coordenadaDoAluno);
                pininhoDoAluno.title(aluno.getNome());
                pininhoDoAluno.snippet(String.format("Nota: %s", String.valueOf(aluno.getNota())));
                googleMap.addMarker(pininhoDoAluno);
            }
        }
    }

    private LatLng PegaCoordenadaDoEndereco(String endereco) {
        Geocoder geocoder = new Geocoder(getContext()); //converte um endereço em coordenadas
        try {
            List<Address> resultados =
                    geocoder.getFromLocationName(endereco, 1);

            if (!resultados.isEmpty()){
               return new LatLng(resultados.get(0).getLatitude(), resultados.get(0).getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
