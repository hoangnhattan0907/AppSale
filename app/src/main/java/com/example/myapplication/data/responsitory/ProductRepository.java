package com.example.myapplication.data.responsitory;

import android.content.Context;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.remote.ApiService;
import com.example.myapplication.data.remote.RetrofitClient;
import com.example.myapplication.data.remote.dto.ProductDTO;

import java.util.List;

import retrofit2.Call;

public class ProductRepository {
    private ApiService apiService;

    public ProductRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<List<ProductDTO>>> getProducts() {
        return apiService.getProducts();
    }
}
