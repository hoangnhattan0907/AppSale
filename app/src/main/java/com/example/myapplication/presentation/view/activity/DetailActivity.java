package com.example.myapplication.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.Product;
import com.example.myapplication.databinding.ActivityDetailBinding;
import com.example.myapplication.databinding.ActivityHomeBinding;
import com.example.myapplication.presentation.view.adapter.SliderAdapter;
import com.example.myapplication.presentation.viewmodel.HomeViewModel;
import com.example.myapplication.utils.StringUtil;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private ViewPager2 viewPager2;
    private Product product;
    private ActivityDetailBinding detailBinding;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(detailBinding.getRoot());

        homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HomeViewModel(DetailActivity.this);
            }
        }).get(HomeViewModel.class);

        initData();
        event();

    }

    private void event() {
        detailBinding.addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeViewModel.addCart(product.getId());
                finishAffinity();
                startActivity(new Intent(DetailActivity.this, HomeActivity.class));
            }
        });
    }

    private void initData() {
        viewPager2 = findViewById(R.id.viewPager);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        product = (Product) bundle.getSerializable("product");
        List<String> url = product.getGallery();

        viewPager2.setAdapter(new SliderAdapter(url, viewPager2, DetailActivity.this));

        detailBinding.textViewName.setText(product.getName());
        detailBinding.textViewAddress.setText(product.getAddress());
        detailBinding.textViewPrice.setText(String.format("%s VND", StringUtil.formatCurrency(product.getPrice())));
    }
}