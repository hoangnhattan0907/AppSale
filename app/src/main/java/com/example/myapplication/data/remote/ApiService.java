package com.example.myapplication.data.remote;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.remote.dto.CartDTO;
import com.example.myapplication.data.remote.dto.ProductDTO;
import com.example.myapplication.data.remote.dto.UserDTO;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/user/sign-in")
    Call<AppResource<UserDTO>> signIn(@Body HashMap<String, Object> body);

    @POST("/user/sign-up")
    Call<AppResource<UserDTO>> register(@Body HashMap<String, Object> body);

    @GET("/product")
    Call<AppResource<List<ProductDTO>>> getProducts();

    @GET("/cart")
    Call<AppResource<CartDTO>> getCart();

    @POST("/cart/add")
    Call<AppResource<CartDTO>> addCart(@Body HashMap<String, Object> body);

    @POST("/cart/update")
    Call<AppResource<CartDTO>> updateCart(@Body HashMap<String, Object> body);

    @POST("/cart/conform")
    Call<AppResource<CartDTO>> cartConform(@Body HashMap<String, Object> body);

    @POST("/order/history")
    Call<AppResource<List<CartDTO>>> orderHistory();
}
