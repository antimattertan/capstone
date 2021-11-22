package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setTitle("정보 목록");

        Intent restaurantIntent = new Intent(this, RestaurantActivity.class);
        Intent galmaetgilIntent = new Intent(this, NewGalmaetgilActivity.class);
        Intent parkingIntent = new Intent(this, ParkingLotActivity.class);
        Intent communityIntent = new Intent(this, MainCommunityActivity.class);
        Intent tourIntent = new Intent(this, TourActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        findViewById(R.id.restaurantCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(restaurantIntent); }
        });

        findViewById(R.id.toiletCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(galmaetgilIntent); }
        });

        findViewById(R.id.parkingCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(parkingIntent); }
        });

        findViewById(R.id.cityparkCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(tourIntent);
            }
        });

        findViewById(R.id.communityCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(communityIntent); }
        });
    }
}