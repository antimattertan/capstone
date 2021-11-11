package com.example.busaninfoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

public class ReviewWriteActivity extends AppCompatActivity {

    public EditText r;
    public String message;
    public RatingBar ratingBar;

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_write);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        findViewById(R.id.writeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r = findViewById(R.id.reviewText);
                message = r.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "메시지를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    String email = user.getEmail();
                    ratingBar = findViewById(R.id.ratingB);
                    float rating = ratingBar.getRating();
                    Review review = new Review();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference(title).push();
                    review.reviewId = email;
                    review.message = message;
                    review.title = title;
                    review.writeTime = System.currentTimeMillis();
                    review.rating = rating;
                    ref.setValue(review);
                    Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}