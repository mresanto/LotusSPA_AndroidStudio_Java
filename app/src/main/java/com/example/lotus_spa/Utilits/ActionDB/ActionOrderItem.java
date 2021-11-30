package com.example.lotus_spa.Utilits.ActionDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.OrderItem;
import com.example.lotus_spa.Class.Product;
import com.example.lotus_spa.Utilits.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ActionOrderItem extends DBHelper {

    public ActionOrderItem(@Nullable Context context){
        super(context);
    }

    public boolean AddOrderItem(OrderItem orderItem ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("OrdCode", 1);
        cv.put("ProdBarCode", orderItem.getProdBarCode());
        cv.put("ProdName", orderItem.getProdName());
        cv.put("ItemUnitaryPrice", orderItem.getItemUnitaryPrice());
        cv.put("ItemAmount", 1);

        long result = db.insert("tbOrderItem ",null,cv);
        db.close();
        if(result == -1)
            return false;
        else
            return true;
    }

    public int VerificarOrderItem(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String sql = "SELECT * FROM tbOrderItem";
            Cursor c = db.rawQuery(sql, null);
            if (c.getCount() != 0)
                return c.getCount();
            else
                return 0;
        }
        catch (Exception e){
            return 0;
        }
    }

    public List<OrderItem> ListarOrderItem(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            List<OrderItem> orderItems = new ArrayList<>();
            String sql = "SELECT * FROM tbOrderItem";
            Cursor c =db.rawQuery(sql, null);

            while(c.moveToNext()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProdBarCode(c.getInt(c.getColumnIndex("ProdBarCode")));
                orderItem.setProdName(c.getString(c.getColumnIndex("ProdName")));
                orderItem.setItemUnitaryPrice(c.getString(c.getColumnIndex("ItemUnitaryPrice")));
                orderItems.add(orderItem);
            }
            return orderItems;
        }
        catch (Exception e){
            return null;
        }

    }

    public boolean DeleteOrderItem(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM tbOrderItem";
        Cursor c = db.rawQuery(sql, null);

        OrderItem orderItem = new OrderItem();
        try {
            c.moveToFirst();
            orderItem.setOrdCode(c.getInt(c.getColumnIndex("OrdCode")));

            Integer i = orderItem.getOrdCode();
            try {
                String[] args = {i.toString()};
                SQLiteDatabase db2 = getWritableDatabase();
                db2.delete("tbOrderItem", "ID=?", args);
            } catch (Exception e) {
                Log.i("INFO DELETE", "OrderItem não deletado");
                return false;
            }
            Log.i("INFO DELETE", "OrderItem deletado");
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
}
