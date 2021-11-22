package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.busaninfoapp.CommWriteActivity;
import com.example.busaninfoapp.R;

public class LikeCommunityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_community);

        Intent mainIntent = new Intent(this, MainCommunityActivity.class);
        Intent likeIntent = new Intent(this, LikeCommunityActivity.class);
        Intent mineIntent = new Intent(this, MineCommunityActivity.class);

        getSupportActionBar().setTitle("좋아요 누른 글");

        findViewById(R.id.home1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mainIntent);
            }
        });

        findViewById(R.id.like1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(likeIntent);
            }
        });

        findViewById(R.id.mine1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(mineIntent);
            }
        });

        findViewById(R.id.floatingActionButton3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LikeCommunityActivity.this, CommWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}