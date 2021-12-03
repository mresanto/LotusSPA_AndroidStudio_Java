package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Packages {

    @SerializedName("PackCode")
    @Expose
    private int packcode;
    @SerializedName("PackPrice")
    @Expose
    private String packprice;
    @SerializedName("PackName")
    @Expose
    private String packname;
    @SerializedName("PackDescription")
    @Expose
    private String packdescription;

    public Packages() {
    }

    public Packages(int packcode, String packprice, String packname, String packdescription) {
        this.packcode = packcode;
        this.packprice = packprice;
        this.packname = packname;
        this.packdescription = packdescription;
    }

    public int getPackcode() {
        return packcode;
    }

    public void setPackcode(int packcode) {
        this.packcode = packcode;
    }

    public String getPackprice() {
        return packprice;
    }

    public void setPackprice(String packprice) {
        this.packprice = packprice;
    }

    public String getPackname() {
        return packname;
    }

    public void setPackname(String packname) {
        this.packname = packname;
    }

    public String getPackdescription() {
        return packdescription;
    }

    public void setPackdescription(String packdescription) {
        this.packdescription = packdescription;
    }
}
