package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Feed;
import com.example.lotus_spa.Class.Resultado;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiCustomer {

    String BASE_URL = "http://10.0.2.2:3000/";

    @Headers("Content-Type: application/json")
    @GET("customer/{email}/{password}")
    Call<Feed> getDataEmail(@Path("email") String email, @Path("password") String password);

    @Headers("Content-Type: application/json")
    @GET("customer/")
    Call<Feed> getData();

    @POST("customer/")
    Call<Customer> createCostumer(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @GET("customer/validation/{email}/{cpf}")
    Call<Feed> getValidation(@Path("email") String email, @Path("cpf") String cpf);
}
