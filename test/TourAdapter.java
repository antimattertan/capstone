package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TourAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private Context context;
    private List<Tour> items;

    public TourAdapter(Context context, List<Tour> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tour, parent, false);
            ItemViewHolder holder = new ItemViewHolder(view);
            view.setOnClickListener(view1 -> {
                Intent intent = new Intent(context, TourMapActivity.class);
                intent.putExtra("tourInfo", items.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            });

            return holder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            populateItemRows((ItemViewHolder) holder, position);
        } else if (holder instanceof LoadingViewHolder) {
            showLoadingView((LoadingViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    private void showLoadingView(LoadingViewHolder holder, int position) {

    }

    private void populateItemRows(ItemViewHolder holder, int position) {
        Tour tour = this.items.get(position);
        holder.setItem(tour);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_mainTitle;
        private TextView textview_addr;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_mainTitle = itemView.findViewById(R.id.textview_mainTitle);
            textview_addr = itemView.findViewById(R.id.textview_addr);
        }

        public void setItem(Tour item) {
            textview_mainTitle.setText("관광코스 " + (getAdapterPosition() + 1) + ": " + item.getMainTitle());
            textview_addr.setText("주소: " + item.getAddress());
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
        }
    }
}