package com.naner.listadetarefas.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "DB_TAREFAS";
    public static String TABELA_TAREFAS = "tarefas";

    public DBHelper(Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL = "CREATE TABLE IF NOT EXISTS " + TABELA_TAREFAS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL, data INTEGER NOT NULL); ";

        Log.i("INFO:", "FIRST!!");
        try {
            Log.i("INFO: ", "CHEGOU AQUI");
            db.execSQL(SQL);
            Log.i("INFO:", "Tabela criada com sucesso.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("INFO:", "CHEGOU NO UPGRADE EIM");
    }
}
