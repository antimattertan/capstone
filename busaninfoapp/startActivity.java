package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

public class startActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        getSupportActionBar().setTitle("부산 관광 정보앱");

        Handler span = new Handler();
        span.postDelayed(new spanActivity(), 2000);
    }

    public class spanActivity implements Runnable {
        @Override
        public void run() {
            Intent loginIntent = new Intent(startActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
}