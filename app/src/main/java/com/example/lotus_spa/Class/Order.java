package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("ordCode")
    @Expose
    private int OrdCode;

    @SerializedName("OrdDate")
    @Expose
    private String ordDate;

    @SerializedName("TotalPrice")
    @Expose
    private double totalPrice;

    @SerializedName("statusOrder")
    @Expose
    private String statusorder;

    @SerializedName("custCPF")
    @Expose
    private String custCPF;

    @SerializedName("payDate")
    @Expose
    private String payDate;

    @SerializedName("isDeleted")
    @Expose
    private String isDeleted;

    @SerializedName("statusPayment")
    @Expose
    private String statusPayment;

    @SerializedName("PayOption")
    @Expose
    private String payOption;

    public Order() {
    }

    public Order(int ordCode, String ordDate, double totalPrice, String statusorder, String custCPF, String payDate, String isDeleted, String statusPayment, String payOption) {
        OrdCode = ordCode;
        this.ordDate = ordDate;
        this.totalPrice = totalPrice;
        this.statusorder = statusorder;
        this.custCPF = custCPF;
        this.payDate = payDate;
        this.isDeleted = isDeleted;
        this.statusPayment = statusPayment;
        this.payOption = payOption;
    }

    public int getOrdCode() {
        return OrdCode;
    }

    public void setOrdCode(int ordCode) {
        OrdCode = ordCode;
    }

    public String getOrdDate() {
        return ordDate;
    }

    public void setOrdDate(String ordDate) {
        this.ordDate = ordDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatusorder() {
        return statusorder;
    }

    public void setStatusorder(String statusorder) {
        this.statusorder = statusorder;
    }

    public String getCustCPF() {
        return custCPF;
    }

    public void setCustCPF(String custCPF) {
        this.custCPF = custCPF;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getStatusPayment() {
        return statusPayment;
    }

    public void setStatusPayment(String statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String getPayOption() {
        return payOption;
    }

    public void setPayOption(String payOption) {
        this.payOption = payOption;
    }
}
