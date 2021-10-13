package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resultado {

    @SerializedName("msg")
    @Expose
    private String msg;

    public String getMensagem() {
        return msg;
    }

    public void setMensagem(String mensagem) {
        this.msg = mensagem;
    }

    @Override
    public String toString() {
        return "Resultado{" +
                "mensagem='" + msg + '\'' +
                '}';
    }
}
