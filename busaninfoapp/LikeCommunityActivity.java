package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busaninfoapp.CommWriteActivity;
import com.example.busaninfoapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import java.util.ArrayList;

public class LikeCommunityActivity extends AppCompatActivity {

    private LikeCommunityAdapter adapter;
    private FirebaseAuth user;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_community);

        Intent mainIntent = new Intent(this, MainCommunityActivity.class);
        Intent likeIntent = new Intent(this, LikeCommunityActivity.class);
        Intent mineIntent = new Intent(this, MineCommunityActivity.class);

        user = FirebaseAuth.getInstance();
        DatabaseReference ref = database.getReference("community");
        RecyclerView recyclerView = findViewById(R.id.recycleV3);
        adapter = new LikeCommunityAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);

        getSupportActionBar().setTitle("좋아요 누른 글");

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ref.orderByChild("writeTime").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Community community = snapshot.getValue(Community.class);
                if(community.getHearts().containsKey(user.getCurrentUser().getUid())) {
                    adapter.addData(community);
                    adapter.notifyItemInserted(adapter.getItemCount()-1);
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

    public class LikeCommunityAdapter extends RecyclerView.Adapter<LikeCommunityAdapter.LikeCommunityViewHolder> {
        ArrayList<Community> mineCommunities = new ArrayList<>();

        @NonNull
        @Override
        public LikeCommunityAdapter.LikeCommunityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new LikeCommunityAdapter.LikeCommunityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_community, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull LikeCommunityAdapter.LikeCommunityViewHolder holder, int position) {
            Community community = mineCommunities.get(position);

            holder.msgText.setText(community.message);
            holder.image.setImageURI(Uri.parse(community.getImageUri()));
            holder.heartCnt.setText(""+community.getHeartCnt());
            holder.commentCnt.setText(""+community.getCommentCnt());

            if (community.getHearts().containsKey(user.getCurrentUser().getUid())) {
                holder.heartImage.setImageResource(R.drawable.fullheart);
            } else {
                holder.heartImage.setImageResource(R.drawable.blankheart);
            }

            holder.heartImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onHeartClick(database.getReference("/community/" + community.postId));
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LikeCommunityActivity.this, DetailCommunityActivity.class);
                    intent.putExtra("postId", community.postId);
                    startActivity(intent);
                }
            });

        }
        @Override
        public int getItemCount() { return mineCommunities.size(); }

        public void addData(Community community) { mineCommunities.add(community); }

        class LikeCommunityViewHolder extends RecyclerView.ViewHolder {

            TextView msgText, heartCnt, commentCnt;
            ImageView image, heartImage, commentImage;

            public LikeCommunityViewHolder(@NonNull View itemView) {
                super(itemView);

                image = itemView.findViewById(R.id.imageView);
                heartCnt = itemView.findViewById(R.id.heartCount);
                commentCnt = itemView.findViewById(R.id.commentCount);
                msgText = itemView.findViewById(R.id.msgText);
                heartImage = itemView.findViewById(R.id.heartImage);
                commentImage = itemView.findViewById(R.id.commentImage);
            }
        }
    }

    private void onHeartClick(DatabaseReference communityRef) {
        communityRef.runTransaction(new Transaction.Handler() {

            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Community community = currentData.getValue(Community.class);
                if (community == null) {
                    return Transaction.success(currentData);
                }
                if (community.getHearts().containsKey(user.getCurrentUser().getUid())) {
                    community.setHeartCnt(community.getHeartCnt()-1);
                    community.getHearts().remove(user.getCurrentUser().getUid());
                } else {
                    community.setHeartCnt(community.getHeartCnt()+1);
                    community.getHearts().put(user.getCurrentUser().getUid(), true);
                }

                currentData.setValue(community);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                overridePendingTransition(0, 0);//인텐트 효과 없애기
                Intent intent = getIntent(); //인텐트
                startActivity(intent); //액티비티 열기
                overridePendingTransition(0, 0);
            }
        });
    }

}