package com.example.myapplication.presentation.view.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.common.AppConstant;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SilderViewHolder>{

    private List<String> listImage;
    private ViewPager2 viewPager2;
    private Context context;

    public SliderAdapter(List<String> listImage, ViewPager2 viewPager2, Context context) {
        this.listImage = listImage;
        this.viewPager2 = viewPager2;
        this.context = context;
    }

    @NonNull
    @Override
    public SilderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SilderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_image_slide,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SilderViewHolder holder, int position) {
        holder.setImage(listImage.get(position));
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    class SilderViewHolder extends RecyclerView.ViewHolder {
        private RoundedImageView imageView;
        SilderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.itemImage);
        }

        void setImage(String string){
            Glide.with(context)
                    .load(AppConstant.BASE_URL + string)
                    .placeholder(R.drawable.ic_logo)
                    .into(imageView);
        }
    }
}
