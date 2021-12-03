package com.example.lotus_spa.Utilits;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 8;
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
                " ProdName TEXT," +
                " ItemUnitaryPrice TEXT," +
                " ItemAmount TEXT);";

        String str2= "CREATE TABLE tbOrder(" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " OrdCode TEXT," +
                " OrdDate TEXT," +
                " OrdTotalPrice TEXT," +
                " StatusOrder TEXT," +
                " IsDeleted TEXT," +
                " PayCode TEXT," +
                " IsDeleted TEXT);";

        try {
            db.execSQL(str);
            db.execSQL(str2);
            Log.i("INFO DB", "Sucesso ao criar a tabela");
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbOrderItem;");
        db.execSQL("DROP TABLE IF EXISTS tbOrder;");
        onCreate(db);
        Log.i("INFO DB", "Banco Atualizado");
    }
}
