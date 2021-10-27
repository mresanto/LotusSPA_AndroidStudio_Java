package com.example.lotus_spa.Utilits.ActionDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Utilits.DBHelper;

public class ActionCustomer extends DBHelper {

    public ActionCustomer(@Nullable Context context){
        super(context);
    }

    public boolean AdicionarCustomer(Customer customer ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("CUSTCODE", customer.getCustcode());
        cv.put("CUSTNAME", customer.getCustname());
        cv.put("CUSTSEX", customer.getCustsex());
        cv.put("CUSTCPF", customer.getCustcpf());
        cv.put("CUSTBIRTHDATE", customer.getCustbirthdate());
        cv.put("CUSTTELEPHONE", customer.getCusttelephone());
        cv.put("CUSTEMAIL", customer.getCustemail());
        cv.put("CUSTPASSWORD", customer.getCustpassword());

        long result = db.insert("tbCUSTOMER ",null,cv);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean VerificarLogin(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM tbCustomer";
            Cursor c = db.rawQuery(sql, null);
            if (c.getCount() == 1)
                return true;
            else
                return false;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean DeleteLogin(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM tbCustomer";
        Cursor c = db.rawQuery(sql, null);

        Customer customer = new Customer();
        try {
            c.moveToFirst();
            customer.setCustcode(c.getInt(c.getColumnIndex("CUSTCODE")));

            Integer i = customer.getCustcode();
            try {
                String[] args = {i.toString()};
                SQLiteDatabase db2 = getWritableDatabase();
                db2.delete("tbCUSTOMER", "CUSTCODE=?", args);
            } catch (Exception e) {
                Log.i("INFO DELETE", "LOGIN não deletado");
                return false;
            }
            Log.i("INFO DELETE", "LOGIN deletado");
            return true;
        }
        catch (Exception e){
            return false;
        }

    }

}
