package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Packages;
import com.example.lotus_spa.Class.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiPackages {

    String BASE_URL = "http://10.0.2.2:5000/api/v1";

    @Headers("Content-Type: application/json")
    @GET("Package")
    Call<List<Packages>> getPackages();

    //@Headers("Content-Type: application/json")
    //@GET("Package/category/{category}")
    //Call<List<Product>> getProductsCategory(@Path("category") String category);
//
    @Headers("Content-Type: application/json")
    @GET("Package/{packcode}")
    Call<List<Packages>> getDetailsPackages(@Path("packcode") int packcode);

}
