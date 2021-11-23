package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class   Customer {

    //Customer
    @SerializedName("custcode")
    @Expose
    private int custcode;

    @SerializedName("CustName")
    @Expose
    private String custname;

    @SerializedName("CustGender")
    @Expose
    private String custsex;

    @SerializedName("CustCPF")
    @Expose
    private String custcpf;

    @SerializedName("CustDtNasc")
    @Expose
    private String custbirthdate;

    @SerializedName("CustTel")
    @Expose
    private String custtelephone;

    @SerializedName("CustEmail")
    @Expose
    private String custemail;

    @SerializedName("CustPassword")
    @Expose
    private String custpassword;

    @SerializedName("CustNumberAddress")
    @Expose
    private String custnumberaddress;

    @SerializedName("CEPAddress")
    @Expose
    private String cep;

    @SerializedName("msg")
    @Expose
    private String mensagem;

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

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCustnumberaddress() {
        return custnumberaddress;
    }

    public void setCustnumberaddress(String custnumberaddress) {
        this.custnumberaddress = custnumberaddress;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}
