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
import com.example.myapplication.data.responsitory.CartRepository;
import com.example.myapplication.data.responsitory.ProductRepository;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartViewModel extends ViewModel {
    private MutableLiveData<AppResource<Cart>> cart = new MutableLiveData<>();
    private CartRepository cartRepository;

    public CartViewModel(Context context) {
        cartRepository = new CartRepository(context);
    }

    public LiveData<AppResource<Cart>> getCart() {
        return cart;
    }

    public void fetchCart() {
        cart.setValue(new AppResource.Loading(null));
        Call<AppResource<CartDTO>> callCart = cartRepository.fetchCart();
        callCart.enqueue(new Callback<AppResource<CartDTO>>() {
            @Override
            public void onResponse(Call<AppResource<CartDTO>> call, Response<AppResource<CartDTO>> response) {
                if (response.isSuccessful()) {
                    CartDTO cartDTO = response.body().data;
                    List<Product> listProduct = new ArrayList<>();
                    for (ProductDTO productDTO : cartDTO.getProducts()) {
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
                    cart.setValue(new AppResource.Success<>(new Cart(cartDTO.getId(), listProduct, cartDTO.getIdUser(), cartDTO.getPrice())));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        cart.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<CartDTO>> call, Throwable t) {
                cart.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void updateCart(String idProduct, String idCart, int quantity) {
        cart.setValue(new AppResource.Loading(null));
        Call<AppResource<CartDTO>> callCart = cartRepository.updateCart(idProduct,idCart,quantity);
        callCart.enqueue(new Callback<AppResource<CartDTO>>() {
            @Override
            public void onResponse(Call<AppResource<CartDTO>> call, Response<AppResource<CartDTO>> response) {
                if (response.isSuccessful()) {
                    CartDTO cartDTO = response.body().data;
                    List<Product> listProduct = new ArrayList<>();
                    for (ProductDTO productDTO : cartDTO.getProducts()) {
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
                    cart.setValue(new AppResource.Success<>(new Cart(cartDTO.getId(), listProduct, cartDTO.getIdUser(), cartDTO.getPrice())));
                } else {
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        String message = jsonObject.getString("message");
                        cart.setValue(new AppResource.Error<>(message));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AppResource<CartDTO>> call, Throwable t) {
                cart.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }

    public void cartConform(String idCart, boolean status) {
        cart.setValue(new AppResource.Loading(null));
        Call<AppResource<CartDTO>> callCart = cartRepository.cartConform(idCart,status);
        callCart.enqueue(new Callback<AppResource<CartDTO>>() {
            @Override
            public void onResponse(Call<AppResource<CartDTO>> call, Response<AppResource<CartDTO>> response) {

            }

            @Override
            public void onFailure(Call<AppResource<CartDTO>> call, Throwable t) {
                cart.setValue(new AppResource.Error<>(t.getMessage()));
            }
        });
    }
}