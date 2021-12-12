package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    private int OrdCode;

    @SerializedName("prodBarCode")
    @Expose
    private int ProdBarCode;

    @SerializedName("itemUnitaryPrice")
    @Expose
    private String ItemUnitaryPrice;

    @SerializedName("itemAmount")
    @Expose
    private int ItemAmount;

    @SerializedName("totalPrice")
    @Expose
    private String TotalPrice;

    @SerializedName("custCPF")
    @Expose
    private String CustCPF;

    private String ProdName;

    public OrderItem() {
    }

    public OrderItem(int ordCode, int prodBarCode, String itemUnitaryPrice, int itemAmount, String totalPrice, String custCPF, String prodName) {
        OrdCode = ordCode;
        ProdBarCode = prodBarCode;
        ItemUnitaryPrice = itemUnitaryPrice;
        ItemAmount = itemAmount;
        TotalPrice = totalPrice;
        CustCPF = custCPF;
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        TotalPrice = totalPrice;
    }

    public String getCustCPF() {
        return CustCPF;
    }

    public void setCustCPF(String custCPF) {
        CustCPF = custCPF;
    }

    public String getProdName() {
        return ProdName;
    }

    public void setProdName(String prodBarName) {
        ProdName = prodBarName;
    }
}
