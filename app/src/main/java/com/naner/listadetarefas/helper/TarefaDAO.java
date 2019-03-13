package com.naner.listadetarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.naner.listadetarefas.model.Tarefa;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TarefaDAO implements ITarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        Log.i("INFO:","TarefaDAO Criado");
        DBHelper db = new DBHelper(context);
        Log.i("INFO:", "DBHelper criado");
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("data", tarefa.getData());

        try {
            escreve.insert(DBHelper.TABELA_TAREFAS, null, cv);
        } catch (Exception e) {
            Log.e("ERRO", "Erro ao salvar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {

        ContentValues cv = new ContentValues();
        cv.put("nome", tarefa.getNomeTarefa());
        cv.put("data", tarefa.getData());

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.update(DBHelper.TABELA_TAREFAS, cv, "id=?", args);
        } catch (Exception e) {
            Log.e("ERRO", "Erro ao atualizar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {

        try {
            String[] args = {tarefa.getId().toString()};
            escreve.delete(DBHelper.TABELA_TAREFAS, "id=?", args);
        } catch (Exception e) {
            Log.e("ERRO", "Erro ao deletar tarefa " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            long dataTarefa = c.getLong(c.getColumnIndex("data"));

/*            String dataDaTarefa = c.getString(c.getColumnIndex("data"));

            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dataDaTarefa);
            } catch (ParseException e) {
                e.printStackTrace();
            }*/

//            Date dataTarefa = new Date(c.getString(c.getColumnIndex("data")));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setData(dataTarefa);
            listaTarefas.add(tarefa);
        }

        return listaTarefas;
    }

    public List<Tarefa> listarHoje() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            long dataTarefa = c.getLong(c.getColumnIndex("data"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setData(dataTarefa);

            Date date = new Date(dataTarefa);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int tarefaAno = calendar.get(Calendar.YEAR);
            int tarefaMes = calendar.get(Calendar.MONTH);
            int tarefaDia = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTime(new Date());
            int atualAno = calendar.get(Calendar.YEAR);
            int atualMes = calendar.get(Calendar.MONTH);
            int atualDia = calendar.get(Calendar.DAY_OF_MONTH);

            if (tarefaAno == atualAno && tarefaMes == atualMes && tarefaDia == atualDia) {
                listaTarefas.add(tarefa);
            }
        }

        return listaTarefas;
    }

    public List<Tarefa> listarAmanha() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            long dataTarefa = c.getLong(c.getColumnIndex("data"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setData(dataTarefa);

            Date date = new Date(dataTarefa);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            int tarefaAno = calendar.get(Calendar.YEAR);
            int tarefaMes = calendar.get(Calendar.MONTH);
            int tarefaDia = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.DATE, 1);
            int amanhaAno = calendar.get(Calendar.YEAR);
            int amanhaMes = calendar.get(Calendar.MONTH);
            int amanhaDia = calendar.get(Calendar.DAY_OF_MONTH);

            if (tarefaAno == amanhaAno && tarefaMes == amanhaMes && tarefaDia == amanhaDia) {
                listaTarefas.add(tarefa);
            }
        }

        return listaTarefas;
    }

    public List<Tarefa> listarOutroDia() {
        List<Tarefa> listaTarefas = new ArrayList<>();

        String sql = "SELECT * FROM " + DBHelper.TABELA_TAREFAS + " ;";
        Cursor c = le.rawQuery(sql, null);

        while(c.moveToNext()) {
            Tarefa tarefa = new Tarefa();

            long id = c.getLong(c.getColumnIndex("id"));
            String nomeTarefa = c.getString(c.getColumnIndex("nome"));
            long dataTarefa = c.getLong(c.getColumnIndex("data"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);
            tarefa.setData(dataTarefa);

            if (dataTarefa == 0) {
                listaTarefas.add(tarefa);
            }
        }

        return listaTarefas;
    }
}
