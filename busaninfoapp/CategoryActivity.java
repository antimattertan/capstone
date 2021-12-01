package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;


public class CategoryActivity extends AppCompatActivity {

    private FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent restaurantIntent = new Intent(this, RestaurantActivity.class);
        Intent galmaetgilIntent = new Intent(this, NewGalmaetgilActivity.class);
        Intent communityIntent = new Intent(this, MainCommunityActivity.class);
        Intent tourIntent = new Intent(this, TourActivity.class);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_new);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("정보 목록");

        user = FirebaseAuth.getInstance();
        findViewById(R.id.rest_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(restaurantIntent); }
        });

        findViewById(R.id.gal_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(galmaetgilIntent); }
        });

        findViewById(R.id.theme_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(tourIntent);
            }
        });

        findViewById(R.id.com_cardView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { startActivity(communityIntent); }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                user.signOut();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                startActivity(loginIntent);
                break;
            case R.id.setting:
                Intent settingIntent = new Intent(this, settingActivity.class);
                startActivity(settingIntent);
        }
        return true;
    }
}