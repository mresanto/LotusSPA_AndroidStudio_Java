package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Cep;
import com.example.lotus_spa.Class.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiCep {

    String BASE_URL = "https://viacep.com.br/ws/";

    @Headers("Content-Type: application/json")
    @GET("{cep}/json")
    Call<Cep> getEnd(@Path("cep") String cep);
}
