package com.example.myapplication.data.responsitory;

import android.content.Context;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.remote.ApiService;
import com.example.myapplication.data.remote.RetrofitClient;
import com.example.myapplication.data.remote.dto.CartDTO;

import java.util.HashMap;

import retrofit2.Call;

public class CartRepository {
    private ApiService apiService;

    public CartRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<CartDTO>> fetchCart() {
        return apiService.getCart();
    }

    public Call<AppResource<CartDTO>> addCart(String idProduct) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id_product", idProduct);
        return apiService.addCart(map);
    }
}
