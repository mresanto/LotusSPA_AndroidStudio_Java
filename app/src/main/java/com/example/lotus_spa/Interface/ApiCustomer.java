package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Feed;
import com.example.lotus_spa.Class.Resultado;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCustomer {

    String BASE_URL = "http://10.0.2.2:5000/api/v1";

    @Headers("Content-Type: application/json")
    @GET("Customer/")
    Call<Customer> getData();

    @POST("Customer/")
    Call<Customer> createCostumer(@Body Customer customer);

    @Headers("Content-Type: application/json")
    @GET("Customer/{cpf}/{email}/{password}")
    Call<List<Customer>> getValidation(@Path("cpf") String cpf, @Path("email") String email, @Path("password") String password);

    @Headers("Content-Type: application/json")
    @GET("Customer/{email}")
    Call<List<Customer>> getByEmail(@Path("email") String email);

    @PUT("Customer/")
    Call<Customer> updateCostumer(@Body Customer customer);


}
