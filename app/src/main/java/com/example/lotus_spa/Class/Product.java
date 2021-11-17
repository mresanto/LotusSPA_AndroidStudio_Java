package com.example.lotus_spa.Class;

import android.media.Image;
import android.widget.ProgressBar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.sql.Blob;

public class Product {

    @SerializedName("prodbarcode")
    @Expose
    private int ProdBarCode;

    @SerializedName("prodname")
    @Expose
    private String ProdName;

    @SerializedName("prodprice")
    @Expose
    private int ProdPrice;

    @SerializedName("isdeleted")
    @Expose
    private Boolean IsDeleted;

    @SerializedName("catcode")
    @Expose
    private int CatCode;

    @SerializedName("catname")
    @Expose
    private String CatName;

    private int ImageProduct;

    public Product(){

    }

    public Product(int prodBarCode, String prodName, int prodPrice, Boolean isDeleted, int catCode, String catName, int imageProduct) {
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

    public int getProdPrice() {
        return ProdPrice;
    }

    public void setProdPrice(int prodPrice) {
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
