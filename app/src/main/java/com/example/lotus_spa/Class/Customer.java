package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class   Customer {

    //Customer
    @SerializedName("custcode")
    @Expose
    private int custcode;

    @SerializedName("custname")
    @Expose
    private String custname;

    @SerializedName("custsex")
    @Expose
    private String custsex;

    @SerializedName("custcpf")
    @Expose
    private String custcpf;

    @SerializedName("custbirthdate")
    @Expose
    private String custbirthdate;

    @SerializedName("custtelephone")
    @Expose
    private String custtelephone;

    @SerializedName("custemail")
    @Expose
    private String custemail;

    @SerializedName("custpassword")
    @Expose
    private String custpassword;

    public int getCustcode() {
        return custcode;
    }

    public void setCustcode(int custcode) {
        this.custcode = custcode;
    }

    public String getCustname() {
        return custname;
    }

    public void setCustname(String custname) {
        this.custname = custname;
    }

    public String getCustsex() {
        return custsex;
    }

    public void setCustsex(String custsex) {
        this.custsex = custsex;
    }

    public String getCustcpf() {
        return custcpf;
    }

    public void setCustcpf(String custcpf) {
        this.custcpf = custcpf;
    }

    public String getCustbirthdate() {
        return custbirthdate;
    }

    public void setCustbirthdate(String custbirthdate) {
        this.custbirthdate = custbirthdate;
    }

    public String getCusttelephone() {
        return custtelephone;
    }

    public void setCusttelephone(String custtelephone) {
        this.custtelephone = custtelephone;
    }

    public String getCustemail() {
        return custemail;
    }

    public void setCustemail(String custemail) {
        this.custemail = custemail;
    }

    public String getCustpassword() {
        return custpassword;
    }

    public void setCustpassword(String custpassword) {
        this.custpassword = custpassword;
    }

}
