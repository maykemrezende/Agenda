package com.example.mayke.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.example.mayke.agenda.modelo.Aluno;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by mayke on 27/10/2017.
 */

public class AlunoDAO extends SQLiteOpenHelper{
    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE alunos (id INTEGER PRIMARY KEY, " +
                    "nome TEXT NOT NULL, endereco TEXT, " +
                    "telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "";
        switch (oldVersion){
            case 1:
                sql = "ALTER TABLE alunos ADD COLUMN caminhoFoto TEXT";

                db.execSQL(sql);
            case 2:
                String tabelaNova = "CREATE TABLE alunos_novo " +
                        "(id CHAR(36) PRIMARY KEY," +
                        "nome TEXT NOT NULL, endereco TEXT, " +
                        "telefone TEXT, site TEXT, nota REAL, caminhoFoto TEXT);";
                db.execSQL(tabelaNova);

                String insertAlunosTabelaNova = "INSERT INTO alunos_novo " +
                        "(id, nome, endereco, telefone, site, nota, caminhoFoto) " +
                        "SELECT id, nome, endereco, telefone, site, nota, caminhoFoto " +
                        "FROM alunos";
                db.execSQL(insertAlunosTabelaNova);

                String excluiTabelaAntiga = "DROP TABLE alunos";
                db.execSQL(excluiTabelaAntiga);

                String alteraNomeTabelaNova = "ALTER TABLE alunos_novo " +
                        "RENAME TO alunos";
                db.execSQL(alteraNomeTabelaNova);

            case 3:
                String buscaAlunos = "SELECT * FROM alunos";
                Cursor cursor = db.rawQuery(buscaAlunos, null);

                List<Aluno> alunos = PopulaAlunos(cursor);

                String atualizaIdAluno = "UPDATE alunos SET id = ? WHERE id = ?";

                for (Aluno aluno:
                     alunos) {
                    db.execSQL(atualizaIdAluno, new String[]{geraUUID(), aluno.getId()});
                }
        }
    }

    private String geraUUID() {
        return UUID.randomUUID().toString();
    }

    public void insere(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        aluno.setId(geraUUID());

        ContentValues dados = getDadosAluno(aluno);

        db.insert("alunos", null, dados);
        //aluno.setId(idAluno);

        if (db.isOpen()) {
            db.close();
        }
    }

    public void deleta(Aluno aluno) {
        //bd que possa ser alterado
        SQLiteDatabase db = getWritableDatabase();

        String[] params = {aluno.getId().toString()};
        db.delete("Alunos", "id = ?", params);

        db.close();
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosAluno(aluno);

        String[] params = {aluno.getId().toString()};
        db.update("Alunos", dados, "id = ?", params);
    }

    public List<Aluno> buscaAlunos() {
        String sql = "SELECT * FROM alunos order by nome";
        //bd q possa ser lido
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);

        List<Aluno> alunos = PopulaAlunos(c);
        if (!c.isClosed()) {
            c.close();
        }

        if (db.isOpen()) {
            db.close();
        }


        return alunos;
    }

    @NonNull
    private List<Aluno> PopulaAlunos(Cursor c) {
        List<Aluno> alunos = new ArrayList<Aluno>();
        while (c.moveToNext()){
            Aluno aluno = new Aluno();

            aluno.setId((c.getString(c.getColumnIndex("id"))));
            aluno.setNome((c.getString(c.getColumnIndex("nome"))));
            aluno.setEndereco((c.getString(c.getColumnIndex("endereco"))));
            aluno.setTelefone((c.getString(c.getColumnIndex("telefone"))));
            aluno.setSite((c.getString(c.getColumnIndex("site"))));
            aluno.setNota((c.getDouble(c.getColumnIndex("nota"))));
            aluno.setCaminhoFoto(c.getString(c.getColumnIndex("caminhoFoto")));

            alunos.add(aluno);
        }
        return alunos;
    }

    private ContentValues getDadosAluno(Aluno aluno) {
        ContentValues dados = new ContentValues();
        dados.put("id", aluno.getId());
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());
        dados.put("caminhoFoto", aluno.getCaminhoFoto());

        return dados;
    }

    public boolean ehAluno(String telefone){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM alunos WHERE telefone = ?";
        Cursor c = db.rawQuery(sql, new String[]{telefone});
        int resultados =  c.getCount();

        c.close();
        db.close();

        return resultados > 0;
    }
}
