package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("ProdBarCode")
    @Expose
    private int ProdBarCode;

    @SerializedName("ProdName")
    @Expose
    private String ProdName;

    @SerializedName("ProdPrice")
    @Expose
    private String ProdPrice;

    @SerializedName("IsDeleted")
    @Expose
    private Boolean IsDeleted;

    @SerializedName("CatCode")
    @Expose
    private int CatCode;

    @SerializedName("CatName")
    @Expose
    private String CatName;

    private int ImageProduct;

    public Product(){

    }

    public Product(int prodBarCode, String prodName, String prodPrice, Boolean isDeleted, int catCode, String catName, int imageProduct) {
        ProdBarCode = prodBarCode;
        ProdName = prodName;
        ProdPrice = prodPrice;
        IsDeleted = isDeleted;
        CatCode = catCode;
        CatName = catName;
        ImageProduct = imageProduct;
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

    public String getProdPrice() {
        return ProdPrice;
    }

    public void setProdPrice(String prodPrice) {
        ProdPrice = prodPrice;
    }

    public Boolean getDeleted() {
        return IsDeleted;
    }

    public void setDeleted(Boolean deleted) {
        IsDeleted = deleted;
    }

    public int getCatCode() {
        return CatCode;
    }

    public void setCatCode(int catCode) {
        CatCode = catCode;
    }

    public String getCatName() {
        return CatName;
    }

    public void setCatName(String catName) {
        CatName = catName;
    }

    public int getImageProduct() {
        return ImageProduct;
    }

    public void setImageProduct(int imageProduct) {
        ImageProduct = imageProduct;
    }
}
