package com.example.myapplication.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.presentation.view.adapter.CartAdapter;
import com.example.myapplication.presentation.viewmodel.CartViewModel;
import com.example.myapplication.utils.StringUtil;


public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding cartBinding;
    private CartViewModel cartViewModel;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartBinding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(cartBinding.getRoot());

        cartViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new CartViewModel(CartActivity.this);
            }
        }).get(CartViewModel.class);

        initData();
        observerData();
        event();
    }



    private void event() {
        cartAdapter.setOnItemClick(new CartAdapter.OnItemClick() {
            @Override
            public void onClickPlus(int position) {
//                Toast.makeText(CartActivity.this, "plus", Toast.LENGTH_SHORT).show();
                cartViewModel.updateCart(cartAdapter.getListProducts().get(position).getId(),
                                        cartAdapter.getId(),
                                cartAdapter.getListProducts().get(position).getQuantity() + 1);
                cartAdapter.setQuantityIncrease(cartAdapter.getListProducts().get(position).getId());
            }

            @Override
            public void onCLickMinus(int position) {
//                Toast.makeText(CartActivity.this, "minus", Toast.LENGTH_SHORT).show();
                if(cartAdapter.getListProducts().get(position).getQuantity() > 1){
                    cartViewModel.updateCart(cartAdapter.getListProducts().get(position).getId(),
                            cartAdapter.getId(),
                            cartAdapter.getListProducts().get(position).getQuantity() - 1);
                    cartAdapter.setQuantityReduce(cartAdapter.getListProducts().get(position).getId());
                }
            }
        });

        cartBinding.createOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.cartConform(cartAdapter.getId(),true);
//                startActivity(new Intent(CartActivity.this, HomeActivity.class));
                finish();
            }
        });
    }

    private void initData() {
        cartAdapter = new CartAdapter();
        cartBinding.recyclerViewProduct.setAdapter(cartAdapter);
        cartBinding.recyclerViewProduct.hasFixedSize();

        cartViewModel.fetchCart();
    }

    private void observerData() {
        cartViewModel.getCart().observe(this, new Observer<AppResource<Cart>>() {
            @Override
            public void onChanged(AppResource<Cart> cartAppResource) {
                switch (cartAppResource.status) {
                    case ERROR:
                        cartBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
                        cartBinding.linearlayoutEmpty.setVisibility(View.VISIBLE);
                        break;
                    case LOADING:
                        cartBinding.includeLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        cartBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
                        cartBinding.constraintLayout.setVisibility(View.VISIBLE);
                        cartAdapter.updateCart(cartAppResource.data);
                        cartBinding.textViewTotalPrice.setText(String.format("%s VND", StringUtil.formatCurrency(cartAppResource.data.getPrice())));
                        break;
                }
            }
        });
    }

}