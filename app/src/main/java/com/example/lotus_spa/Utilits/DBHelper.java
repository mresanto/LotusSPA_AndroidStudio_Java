package com.example.lotus_spa.Utilits;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 7;
    private static String nome = "Lotus_Spa";

    public DBHelper(@Nullable Context context){
        super(context,nome,null,versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String str= "CREATE TABLE tbOrderItem(" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " OrdCode TEXT," +
                " ProdBarCode TEXT," +
                " ItemUnitaryPrice TEXT," +
                " ItemAmount TEXT);";
        try {
            db.execSQL(str);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbOrderItem;");
        onCreate(db);
        Log.i("INFO DB", "Banco Atualizado");
    }
}
