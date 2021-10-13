package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestApi {

    @SerializedName("tipo")
    @Expose
    private String tipo;

    @SerializedName("descricao")
    @Expose
    private String descricao;

    @SerializedName("url")
    @Expose
    private String url;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "RequestApi{" +
                "tipo='" + tipo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
