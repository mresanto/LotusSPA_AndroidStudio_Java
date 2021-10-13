package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Response {

    @SerializedName("quantidade")
    @Expose
    private String quantidade;

    @SerializedName("customer")
    @Expose
    private ArrayList<Customer> customer;

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public ArrayList<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(ArrayList<Customer> customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Response{" +
                "quantidade='" + quantidade + '\'' +
                ", customer=" + customer +
                '}';
    }
}
