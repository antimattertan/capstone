package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;

public class NewGalmaetgilActivity extends AppCompatActivity {

    private static final int PER_DATAS = 10;

    private boolean isLoading;

    // 현재 스크롤 후 리사이클러뷰에 보여지는 데이터 리스트
    private ArrayList<Galmaetgil> recyclerDatas;

    // Json 통해 불러와진 전체 데이터 리스트
    private ArrayList<Galmaetgil> datas;

    private GalmaetgilParser parser;

    private RecyclerView recyclerView;

    private GalmaetgilAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_galmaetgil);

        parser = new GalmaetgilParser(NewGalmaetgilActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getDatas();
        initAdapter();
        initScrollListener();
    }

    private void getDatas() {
        this.recyclerDatas = new ArrayList<>();
        this.datas = parser.getDatas();

        // insert 10 datas
        for (int i = 0; i < PER_DATAS; i++) {
            this.recyclerDatas.add(this.datas.get(i));
        }
    }

    private void initAdapter() {
        adapter = new GalmaetgilAdapter(this.recyclerDatas);
        recyclerView.setAdapter(adapter);
    }

    private void initScrollListener() {
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == recyclerDatas.size() - 1) {
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        recyclerDatas.add(null);
        recyclerView.post(() -> {
            adapter.notifyItemInserted(recyclerDatas.size() - 1);
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerDatas.remove(recyclerDatas.size() - 1);
                int scrollPosition = recyclerDatas.size();
                recyclerView.post(() -> {
                    adapter.notifyItemRemoved(scrollPosition);
                });
                int currentSize = scrollPosition;
                int nextLimit = currentSize + PER_DATAS;

                for (int i = currentSize; i < nextLimit; i++) {
                    if (i == datas.size()) {
                        return;
                    }
                    recyclerDatas.add(datas.get(i));
                }

                recyclerView.post(() -> {
                    adapter.notifyDataSetChanged();
                });
                isLoading = false;
            }
        }, 1000);
    }
}