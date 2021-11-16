package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.busaninfoapp.communityFrag.LikeFragment;
import com.example.busaninfoapp.communityFrag.MainCommFragment;
import com.example.busaninfoapp.communityFrag.MineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CommunityActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        bottomNavigationView = findViewById(R.id.bottomNavi);

        getSupportActionBar().setTitle("글 목록");
        getSupportFragmentManager().beginTransaction().add(R.id.commFragment, new MainCommFragment()).commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item1:
                        getSupportActionBar().setTitle("글 목록");
                        getSupportFragmentManager().beginTransaction().replace(R.id.commFragment, new MainCommFragment()).commit();
                        break;
                    case R.id.item2:
                        getSupportActionBar().setTitle("즐겨 찾기");
                        getSupportFragmentManager().beginTransaction().replace(R.id.commFragment, new LikeFragment()).commit();
                        break;
                    case R.id.item3:
                        getSupportActionBar().setTitle("작성한 글 목록");
                        getSupportFragmentManager().beginTransaction().replace(R.id.commFragment, new MineFragment()).commit();
                        break;
                }

                return true;
            }
        });

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommunityActivity.this, CommWriteActivity.class);
                startActivity(intent);
            }
        });
    }
}