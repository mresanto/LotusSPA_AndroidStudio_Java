package com.example.lotus_spa.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Feed {

    @SerializedName("response")
    @Expose
    private Response response;
    private List<Resultado> resultado;


    public void setResponse(Response response) {
        this.response = response;
    }
    public Response getResponse() {
        return  response;
    }

    public List<Resultado> getResultado() {
        return resultado;
    }
    public void setResultado(List<Resultado> resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "response=" + response +
                ", resultado=" + resultado +
                '}';
    }
}
