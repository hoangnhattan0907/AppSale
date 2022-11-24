package com.example.myapplication.presentation.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.data.model.AppResource;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;
import com.example.myapplication.databinding.ActivityHistoryBinding;
import com.example.myapplication.presentation.view.adapter.HistoryAdapter;
import com.example.myapplication.presentation.viewmodel.HistoryViewModel;
import com.example.myapplication.utils.StringUtil;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ActivityHistoryBinding historyBinding;
    private HistoryAdapter historyAdapter;
    private HistoryViewModel historyViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyBinding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(historyBinding.getRoot());

        historyViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) new HistoryViewModel(HistoryActivity.this);
            }
        }).get(HistoryViewModel.class);

        initData();
        observerData();
        event();
    }

    private void event() {
        historyAdapter.setOnItemClick(new HistoryAdapter.OnItemClick() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(HistoryActivity.this, HistoryDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cart_detail", historyAdapter.getListCarts().get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void observerData() {
        historyViewModel.getListProducts().observe(this, new Observer<AppResource<List<Cart>>>() {
            @Override
            public void onChanged(AppResource<List<Cart>> listAppResource) {
                switch (listAppResource.status) {
                    case ERROR:
                        historyBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
                        historyBinding.linearlayoutEmpty.setVisibility(View.VISIBLE);
                        break;
                    case LOADING:
                        historyBinding.includeLoading.layoutLoading.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        historyBinding.includeLoading.layoutLoading.setVisibility(View.GONE);
                        if(listAppResource.data.size() == 0 || listAppResource.data.isEmpty()){
                            historyBinding.linearlayoutEmpty.setVisibility(View.VISIBLE);
                        }else{
                            historyAdapter.updateCart(listAppResource.data);
                        }
                        break;
                }
            }
        });
    }

    private void initData() {
        historyAdapter = new HistoryAdapter();
        historyBinding.recyclerViewCart.setAdapter(historyAdapter);
        historyBinding.recyclerViewCart.hasFixedSize();

        historyViewModel.fetchCarts();
    }
}