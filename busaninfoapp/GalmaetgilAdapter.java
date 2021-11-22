package com.example.busaninfoapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GalmaetgilAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    private List<Galmaetgil> items;

    public GalmaetgilAdapter(List<Galmaetgil> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_galmaetgil, parent, false);
            return new ItemViewHolder(view);
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
        Galmaetgil galmaetgil = this.items.get(position);
        holder.setItem(galmaetgil);
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView textview_gugan;
        private TextView textview_startAddr;
        private TextView textview_gmCourse;
        private TextView textview_gmText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textview_gugan = itemView.findViewById(R.id.textview_gugan);
            textview_startAddr = itemView.findViewById(R.id.textview_startAddr);
            textview_gmCourse = itemView.findViewById(R.id.textview_gmCourse);
            textview_gmText = itemView.findViewById(R.id.textview_gmText);
        }

        public void setItem(Galmaetgil item) {
            textview_gugan.setText(item.getGugan());
            textview_startAddr.setText("코스 시작 주소: " + item.getStartAddr());
            textview_gmCourse.setText("갈맷길 코스 정보: " + item.getGmCourse());
            textview_gmText.setText("갈맷길 코스 관련 정보: " + item.getGmText());
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
