package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RestaurantReviewActivity extends AppCompatActivity {

    private static final String TAG = "ReviewActivity";
    private RestaurantAdapter adapter;
    TextView ratingText;
    RatingBar rateBar;

    ArrayList<Review> reviews = new ArrayList<>();
    RecyclerView recyclerView;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_review);

        Intent intent = getIntent();
        String title = intent.getExtras().getString("name");
        ratingText = findViewById(R.id.ratingText);
        rateBar = findViewById(R.id.ratingBar2);

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantReviewActivity.this, ReviewWriteActivity.class);
                intent.putExtra("name", title);
                startActivity(intent);
            }
        });

        DatabaseReference ref = db.getReference("/"+title);
        recyclerView = findViewById(R.id.RecyclerView);
        adapter = new RestaurantAdapter();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);



        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ref.orderByChild("writeTime").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Review addReview = snapshot.getValue(Review.class);
                reviews.add(addReview);
                adapter.notifyItemInserted(reviews.size()-1);
                float rate = 0.0f;
                float sum = 0.0f;
                if(reviews.size() != 0) {
                    for(int i = 0; i < reviews.size(); i++) {
                        sum += reviews.get(i).rating;
                    }
                    rate = sum / reviews.size();
                    rate = (int) rate;
                    rateBar.setRating(rate);
                    ratingText.setText("" + rate);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

        @NonNull
        @Override
        public RestaurantAdapter.RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new RestaurantAdapter.RestaurantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_review, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RestaurantAdapter.RestaurantViewHolder holder, int position) {
            Review review = reviews.get(position);

            holder.userId.setText(review.reviewId);
            holder.reviewText.setText(review.message);
            holder.ratingBar.setRating(review.rating);
            holder.writeDay.setText(getTime(review.writeTime));

        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }

        class RestaurantViewHolder extends RecyclerView.ViewHolder {

            TextView userId, reviewText, writeDay;
            RatingBar ratingBar;

            public RestaurantViewHolder(@NonNull View itemView) {
                super(itemView);

                userId = itemView.findViewById(R.id.userIdText);
                reviewText = itemView.findViewById(R.id.reviewT);
                writeDay = itemView.findViewById(R.id.writeDayText);
                ratingBar = itemView.findViewById(R.id.ratingBar4);
            }
        }
    }

    public String getTime(long writeTime) {
        Date date = new Date(writeTime);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
        String time = dateFormat.format(date);

        return time;
    }
}