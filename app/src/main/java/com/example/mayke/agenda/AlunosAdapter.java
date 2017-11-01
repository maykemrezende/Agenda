package com.example.mayke.agenda;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        TextView view = new TextView(context);
        Aluno aluno = alunos.get(position);
        view.setText(aluno.toString());

        return view;
    }
}
