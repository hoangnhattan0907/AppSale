package com.example.myapplication.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.databinding.ActivityCartBinding;
import com.example.myapplication.databinding.ActivityHistoryDetailBinding;
import com.example.myapplication.presentation.view.adapter.CartAdapter;
import com.example.myapplication.presentation.view.adapter.HistoryAdapter;
import com.example.myapplication.presentation.view.adapter.HistoryItemAdapter;
import com.example.myapplication.presentation.viewmodel.CartViewModel;
import com.example.myapplication.utils.StringUtil;

public class HistoryDetailActivity extends AppCompatActivity {
    private HistoryItemAdapter historyItemAdapter;
    private Cart cart;
    private ActivityHistoryDetailBinding historyDetailBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_detail);
        historyDetailBinding = ActivityHistoryDetailBinding.inflate(getLayoutInflater());
        setContentView(historyDetailBinding.getRoot());

        initData();
        event();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        cart = (Cart) bundle.getSerializable("cart_detail");

        historyItemAdapter = new HistoryItemAdapter();
        historyDetailBinding.recyclerViewHistoryDetail.setAdapter(historyItemAdapter);
        historyDetailBinding.recyclerViewHistoryDetail.hasFixedSize();

        historyItemAdapter.updateCart(cart);
        historyDetailBinding.textViewTotalPrice.setText("Tổng tiền của đơn hàng: " + String.format("%s VND", StringUtil.formatCurrency(cart.getPrice())));
    }

    private void event() {
    }
}