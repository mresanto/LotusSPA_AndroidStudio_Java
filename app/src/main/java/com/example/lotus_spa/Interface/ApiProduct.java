package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface ApiProduct {

    String BASE_URL = "https://apilotusspa.herokuapp.com/api/v1";

    @Headers("Content-Type: application/json")
    @GET("Product/")
    Call<List<Product>> getProducts();

    @Headers("Content-Type: application/json")
    @GET("Product/category/{category}")
    Call<List<Product>> getProductsCategory(@Path("category") String category);

    @Headers("Content-Type: application/json")
    @GET("Product/{prodcode}")
    Call<List<Product>> getDetailsProduct(@Path("prodcode") int procode);

}
