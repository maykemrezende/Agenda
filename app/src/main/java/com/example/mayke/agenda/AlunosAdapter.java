package com.example.mayke.agenda;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mayke.agenda.modelo.Aluno;

import java.util.List;

/**
 * Created by mayke on 01/11/2017.
 */

public class AlunosAdapter extends BaseAdapter{

    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.context = context ;
        this.alunos  = alunos;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Aluno aluno = alunos.get(position);

        //inflater transforma o xml em uma view
        LayoutInflater inflater = LayoutInflater.from(context);

        //convertview não deixa instanciar a view sempre, ele reaproveita

        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        //busca o campo nome dentro da view inflada pelo list_item.xml, pq o campo está nesse xml e não em outro
        TextView campoNome = view.findViewById(R.id.itemNome);
        TextView campoTelefone = view.findViewById(R.id.itemTelefone);
        ImageView campoFoto = view.findViewById(R.id.itemFoto);
        TextView campoSite = view.findViewById(R.id.id_site);
        TextView campoEndereco = view.findViewById(R.id.item_endereco);

        campoNome.setText(aluno.getNome());
        campoTelefone.setText(aluno.getTelefone());

        if ((campoEndereco != null) && (campoSite != null)) {
            campoEndereco.setText(aluno.getEndereco());
            campoSite.setText(aluno.getSite());
        }

        String caminhoFoto = aluno.getCaminhoFoto();

        if (caminhoFoto != null) {
            //tratamento de adicionar a foto ao ImageView
            Bitmap bitmapFoto = BitmapFactory.decodeFile(caminhoFoto);
            Bitmap bitmapFotoReduzido = Bitmap.createScaledBitmap(bitmapFoto, 100, 100, true);
            campoFoto.setImageBitmap(bitmapFotoReduzido);
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
