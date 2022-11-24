package com.example.myapplication.presentation.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;
import com.example.myapplication.data.remote.dto.CartDTO;
import com.example.myapplication.data.remote.dto.ProductDTO;
import com.example.myapplication.data.responsitory.HistoryRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryViewModel extends ViewModel {
    private MutableLiveData<AppResource<List<Cart>>> listCarts = new MutableLiveData<>();
    private HistoryRepository historyRepository;

    public HistoryViewModel(Context context) {
        historyRepository = new HistoryRepository(context);
    }

    public LiveData<AppResource<List<Cart>>> getListProducts() {
        return listCarts;
    }

    public void fetchCarts() {
        listCarts.setValue(new AppResource.Loading(null));
        Call<AppResource<List<CartDTO>>> callCarts = historyRepository.fetchOrder();
        callCarts.enqueue(new Callback<AppResource<List<CartDTO>>>() {
            @Override
            public void onResponse(Call<AppResource<List<CartDTO>>> call, Response<AppResource<List<CartDTO>>> response) {
                if (response.isSuccessful()) {
                    List<CartDTO> listCartDTO = response.body().data;
                    List<Cart> listCart = new ArrayList<>();
                    for (CartDTO cartDTO : listCartDTO) {
                        List<Product> listProduct = new ArrayList<>();
                        for(ProductDTO productDTO : cartDTO.getProducts()){
                            listProduct.add(new Product(
                                    productDTO.getId(),
                                    productDTO.getName(),
                                    productDTO.getAddress(),
                                    productDTO.getPrice(),
                                    productDTO.getImg(),
                                    productDTO.getQuantity(),
                                    productDTO.getGallery())
                            );
                        }
                        listCart.add(new Cart(cartDTO.getId(), listProduct, cartDTO.getIdUser(), cartDTO.getPrice(), cartDTO.getDateCreated()));
                    }
                    listCarts.setValue(new AppResource.Success<>(listCart));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        listCarts.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<List<CartDTO>>> call, Throwable t) {
                listCarts.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}
