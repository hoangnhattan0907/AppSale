package com.example.myapplication.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.data.model.Cart;
import com.example.myapplication.data.model.Product;

import com.example.myapplication.databinding.LayoutItemHistoryBinding;
import com.example.myapplication.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HistoryAdapter  extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private List<Cart> listCarts;
    private Context context;
    private OnItemClick onItemClick;

    public HistoryAdapter() {
        listCarts = new ArrayList<>();
    }

    public void updateCart(List<Cart> data) {
        this.listCarts = sortedList(data);
        notifyDataSetChanged();
    }

    public List<Cart> getListCarts(){
        return listCarts;
    }

    public List<Cart> sortedList(List<Cart> data){
        List<Cart> sortedList = new ArrayList();
        for(int i = data.size()-1 ;i >= 0 ;i--){
            sortedList.add(data.get(i));
        }
        return sortedList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HistoryViewHolder(LayoutItemHistoryBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.bind(context, listCarts.get(position));

    }

    public int getItemCount() {
        return listCarts.size();
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder {

        LayoutItemHistoryBinding binding;

        public HistoryViewHolder(@NonNull LayoutItemHistoryBinding view) {
            super(view.getRoot());
            binding = view;

            binding.itemHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick != null) {
                        onItemClick.onClick(getAdapterPosition());
                    }
                }
            });


        }

        public void bind(Context context, Cart cart) {
            binding.textViewDate.setText(cart.getDateCreated().substring(0,10));
            binding.textViewTotalPrice.setText("Tổng tiền: " + String.format("%s VND", StringUtil.formatCurrency(cart.getPrice())));
        }
    }
    public interface OnItemClick {
        void onClick(int position);
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

}