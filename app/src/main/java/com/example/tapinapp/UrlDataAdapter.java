package com.example.tapinapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tapinapp.Model.UrlData;

import java.util.List;

public class UrlDataAdapter extends RecyclerView.Adapter<UrlDataAdapter.UrlDataViewHolder> {

    Context context;
    List<UrlData> urlDataList;

    public UrlDataAdapter(Context context, List<UrlData> urlDataList) {
        this.context = context;
        this.urlDataList = urlDataList;
    }

    @NonNull
    @Override
    public UrlDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UrlDataViewHolder(LayoutInflater.from(context).inflate(
                R.layout.custom_recycler_layout,
                parent,
                false
        ));
    }

    @Override
    public void onBindViewHolder(@NonNull UrlDataViewHolder holder, int position) {

        Glide.with(context).load(urlDataList.get(position).getImgUrl()).into(holder.imageView);
        holder.url.setText(urlDataList.get(position).getUrl());
        holder.title.setText("Title : "+urlDataList.get(position).getTitle());
        holder.des.setText("Description : "+urlDataList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return urlDataList.size();
    }

    public class UrlDataViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView url;
        TextView title;
        TextView des;

        public UrlDataViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.customImageView);
            url = itemView.findViewById(R.id.customUrl);
            title = itemView.findViewById(R.id.customTitle);
            des = itemView.findViewById(R.id.customDes);
        }
    }
}
