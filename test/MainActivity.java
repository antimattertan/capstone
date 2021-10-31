package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent restaurantIntent = new Intent(this, RestaurantActivity.class);
        Intent galmaetgilIntent = new Intent(this, galmaetgilActivity.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        findViewById(R.id.restaurantCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(restaurantIntent); }
        });

        findViewById(R.id.toiletCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(galmaetgilIntent); }
        });
    }
}