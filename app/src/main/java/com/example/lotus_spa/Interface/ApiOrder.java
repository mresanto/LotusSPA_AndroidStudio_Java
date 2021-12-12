package com.example.lotus_spa.Interface;

import com.example.lotus_spa.Class.Customer;
import com.example.lotus_spa.Class.Order;
import com.example.lotus_spa.Class.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiOrder {

    String BASE_URL = "http://10.0.2.2:5000/api/v1";

    @Headers("Content-Type: application/json")
    @GET("Order/")
    Call<List<Order>> getData();

    @POST("Order/")
    Call<Order> createOrder(@Body Order order);

    @POST("OrderItem/")
    Call<OrderItem> createOrderItem(@Body OrderItem orderItem);
}
