package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settingActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    ImageView userPhoto;
    TextView userName, userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("설정");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        db = FirebaseDatabase.getInstance();

        userName = findViewById(R.id.userN);
        userEmail = findViewById(R.id.userE);
        userPhoto = findViewById(R.id.userPhoto);

        DatabaseReference ref = db.getReference("users");

        String uid = user.getUid();


        ref.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User usr = dataSnapshot.getValue(User.class);
                userName.setText(usr.getUserName());
                userEmail.setText(usr.getUserEmail());
                Uri uri = Uri.parse(usr.getUserImageUri());
                RequestOptions cropOptions = new RequestOptions();
                Glide.with(getApplicationContext()).load(uri).apply(cropOptions.optionalCircleCrop()).into(userPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        findViewById(R.id.userInfoCard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(settingActivity.this, userInfoActivity.class);
                startActivity(intent);
            }
        });
    }
}