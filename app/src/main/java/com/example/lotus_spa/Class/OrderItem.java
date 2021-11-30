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

    private String ProdName;

    @SerializedName("ItemUnitaryPrice")
    @Expose
    private String ItemUnitaryPrice;

    @SerializedName("ItemAmount")
    @Expose
    private int ItemAmount;

    public OrderItem() {
    }

    public OrderItem(int ordCode, int prodBarCode, String itemUnitaryPrice, int itemAmount, String prodName) {
        OrdCode = ordCode;
        ProdBarCode = prodBarCode;
        ItemUnitaryPrice = itemUnitaryPrice;
        ItemAmount = itemAmount;
        ProdName = prodName;
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

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodName) {
        ProdName = prodName;
    }

    public String getItemUnitaryPrice() {
        return ItemUnitaryPrice;
    }

    public void setItemUnitaryPrice(String itemUnitaryPrice) {
        ItemUnitaryPrice = itemUnitaryPrice;
    }

    public int getItemAmount() {
        return ItemAmount;
    }

    public void setItemAmount(int itemAmount) {
        ItemAmount = itemAmount;
    }
}
