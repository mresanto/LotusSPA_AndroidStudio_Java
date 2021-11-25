package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("OrdCode")
    @Expose
    private int OrdCode;

    @SerializedName("ProdBarCode")
    @Expose
    private int ProdBarCode;

    @SerializedName("ItemUnitaryPrice")
    @Expose
    private float ItemUnitaryPrice;

    @SerializedName("ItemAmount")
    @Expose
    private int ItemAmount;

    public OrderItem() {
    }

    public OrderItem(int ordCode, int prodBarCode, float itemUnitaryPrice, int itemAmount) {
        OrdCode = ordCode;
        ProdBarCode = prodBarCode;
        ItemUnitaryPrice = itemUnitaryPrice;
        ItemAmount = itemAmount;
    }

    public int getOrdCode() {
        return OrdCode;
    }

    public void setOrdCode(int ordCode) {
        OrdCode = ordCode;
    }

    public int getProdBarCode() {
        return ProdBarCode;
    }

    public void setProdBarCode(int prodBarCode) {
        ProdBarCode = prodBarCode;
    }

    public float getItemUnitaryPrice() {
        return ItemUnitaryPrice;
    }

    public void setItemUnitaryPrice(float itemUnitaryPrice) {
        ItemUnitaryPrice = itemUnitaryPrice;
    }

    public int getItemAmount() {
        return ItemAmount;
    }

    public void setItemAmount(int itemAmount) {
        ItemAmount = itemAmount;
    }
}
