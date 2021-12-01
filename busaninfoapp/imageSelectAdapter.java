package com.example.busaninfoapp;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class imageSelectAdapter extends RecyclerView.Adapter<imageSelectAdapter.ViewHolder>{
    private ArrayList<Uri> mData = null;
    private Context mContext = null;

    imageSelectAdapter(ArrayList<Uri> list, Context context) {
        mData = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.images_item, parent, false);
        imageSelectAdapter.ViewHolder viewHolder = new imageSelectAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri image_uri = mData.get(position);

        Glide.with(mContext).load(image_uri).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.imageView5);
        }
    }
}
