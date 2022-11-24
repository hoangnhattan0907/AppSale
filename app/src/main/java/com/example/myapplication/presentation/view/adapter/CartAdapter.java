package com.example.myapplication.presentation.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.common.AppConstant;
import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;


import com.example.myapplication.databinding.LayoutItemCartProductBinding;
import com.example.myapplication.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Cart cart;
    private List<Product> listProducts ;
    private Context context;
    private OnItemClick onItemClick;

    public CartAdapter() {
        listProducts = new ArrayList<>();
    }

    public void updateCart(Cart data) {
        this.cart = data;
        listProducts.clear();
        listProducts.addAll(data.getProducts());
        notifyDataSetChanged();
    }

    public List<Product> getListProducts() {
        return listProducts;
    }

    public String getId(){
        return cart.getId();
    }

    public void setQuantityIncrease(String idProduct){
        for(Product product : listProducts){
            if (product.getId().equals(idProduct)){
                product.setQuantity(product.getQuantity()+1);
            }
        }
        notifyDataSetChanged();
    }

    public void setQuantityReduce(String idProduct){
        for(Product product : listProducts){
            if (product.getId().equals(idProduct)){
                if(product.getQuantity() > 1){
                    product.setQuantity(product.getQuantity()-1);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CartAdapter.CartViewHolder(LayoutItemCartProductBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.bind(context, listProducts.get(position));

    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }

    class CartViewHolder extends RecyclerView.ViewHolder {

        LayoutItemCartProductBinding binding;

        public CartViewHolder(@NonNull LayoutItemCartProductBinding view) {
            super(view.getRoot());
            binding = view;

            binding.btnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onClickPlus(getAdapterPosition());
                    }
                }
            });
            binding.btnMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onCLickMinus(getAdapterPosition());
                    }
                }
            });


        }

        public void bind(Context context, Product product) {
            binding.textViewName.setText(product.getName());
            binding.textViewPrice.setText("Giá: " + String.format("%s VND", StringUtil.formatCurrency(product.getPrice())));
            Glide.with(context)
                    .load(AppConstant.BASE_URL + product.getImg())
                    .placeholder(R.drawable.ic_logo)
                    .into(binding.imageView);

            binding.textviewQuantity.setText(product.getQuantity() + "");

        }
    }
    public interface OnItemClick {
        void onClickPlus(int position);
        void onCLickMinus(int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }


}