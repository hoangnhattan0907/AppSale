package com.example.myapplication.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.common.AppConstant;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;
import com.example.myapplication.databinding.LayoutItemHistoryProductBinding;
import com.example.myapplication.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {
    private List<Product> listProducts ;
    private Context context;

    public HistoryItemAdapter(){
        listProducts = new ArrayList<>();
    }
    public void updateCart(Cart data) {
        listProducts.clear();
        listProducts.addAll(data.getProducts());
        notifyDataSetChanged();
    }

    public List<Product> getListProducts() {
        return listProducts;
    }


    @NonNull
    @Override
    public HistoryItemAdapter.HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HistoryItemAdapter.HistoryItemViewHolder(LayoutItemHistoryProductBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemAdapter.HistoryItemViewHolder holder, int position) {
        holder.bind(context, listProducts.get(position));

    }

    public int getItemCount() {
        return listProducts.size();
    }

    class HistoryItemViewHolder extends RecyclerView.ViewHolder {

        LayoutItemHistoryProductBinding binding;

        public HistoryItemViewHolder(@NonNull   LayoutItemHistoryProductBinding view) {
            super(view.getRoot());
            binding = view;

        }

        public void bind(Context context, Product product) {
            binding.textViewName.setText(product.getName());
            binding.textviewPrice.setText("Giá: " + String.format("%s VND", StringUtil.formatCurrency(product.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL + product.getImg())
                    .placeholder(R.drawable.ic_logo)
                    .into(binding.imageView);

            binding.textViewQuantity.setText("Số lượng: " +product.getQuantity());
        }
    }

}