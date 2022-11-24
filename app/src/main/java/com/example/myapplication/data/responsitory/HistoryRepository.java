package com.example.myapplication.data.responsitory;

import android.content.Context;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.remote.ApiService;
import com.example.myapplication.data.remote.RetrofitClient;
import com.example.myapplication.data.remote.dto.CartDTO;

import java.util.List;

import retrofit2.Call;

public class HistoryRepository {
    private ApiService apiService;

    public HistoryRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public Call<AppResource<List<CartDTO>>> fetchOrder() {
        return apiService.orderHistory();
    }
}
