package com.example.lotus_spa.Utilits.ActionDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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

    public boolean UpdateOrderItem(List<OrderItem> orderItem){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("OrdCode", 2);
        long result = -1;
        for(int i = 0; i < orderItem.size(); i++){
            result = db.update("tbOrderItem",cv,"ProdBarCode=?", new String[]{Integer.toString(orderItem.get(i).getProdBarCode())});
        }
        db.close();
        if(result == -1)
            return false;
        else
            return true;

    }

    public boolean UpdateAllOrderItem(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("OrdCode", 1);
        long result = db.update("tbOrderItem",cv,null, null);
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

    public boolean DetailsOrderItem(OrderItem orderItem){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            String sql = "SELECT * FROM tbOrderItem where ProdBarCode = ? ";
            String b = Integer.toString(orderItem.getProdBarCode());
            String[] a = new String[] {Integer.toString(orderItem.getProdBarCode())};
            Log.i("INFO DB Details", b);
            Cursor c = db.rawQuery(sql, a);
            if (c.getCount() != 0)
                return false;
            else
                return true;
        }
        catch (Exception e){
            return false;
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
                orderItem.setOrdCode(c.getInt(c.getColumnIndex("OrdCode")));
                orderItem.setItemUnitaryPrice(c.getString(c.getColumnIndex("ItemUnitaryPrice")));
                orderItems.add(orderItem);
            }
            return orderItems;
        }
        catch (Exception e){
            return null;
        }

    }

    public List<OrderItem> ListarOrderItemOrd(){
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            List<OrderItem> orderItems = new ArrayList<>();
            String sql = "SELECT * FROM tbOrderItem WHERE OrdCode = 2";
            Cursor c =db.rawQuery(sql, null);

            while(c.moveToNext()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setProdBarCode(c.getInt(c.getColumnIndex("ProdBarCode")));
                orderItem.setProdName(c.getString(c.getColumnIndex("ProdName")));
                orderItem.setOrdCode(c.getInt(c.getColumnIndex("OrdCode")));
                orderItem.setItemUnitaryPrice(c.getString(c.getColumnIndex("ItemUnitaryPrice")));
                orderItems.add(orderItem);
            }
            return orderItems;
        }
        catch (Exception e){
            return null;
        }

    }

    public boolean DeleteOrderItem(OrderItem orderItem){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM tbOrderItem";
        Cursor c = db.rawQuery(sql, null);

        try {
            c.moveToFirst();
            orderItem.setOrdCode(c.getInt(c.getColumnIndex("OrdCode")));

            Integer i = orderItem.getProdBarCode();
            try {
                String[] args = {i.toString()};
                SQLiteDatabase db2 = getWritableDatabase();
                db2.delete("tbOrderItem", "ProdBarCode=?", args);
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
