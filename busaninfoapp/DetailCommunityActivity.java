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
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class DetailCommunityActivity extends AppCompatActivity {

    ArrayList<Comment> comments = new ArrayList<>();
    ImageView imageView;
    TextView textView;
    CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_community);

        String postId = getIntent().getStringExtra("postId");
        imageView = findViewById(R.id.imageView3);
        textView = findViewById(R.id.com_message);
        adapter = new CommentAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerView recyclerView = findViewById(R.id.comment_recycle);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseDatabase.getInstance().getReference("/community/" + postId).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Community community = dataSnapshot.getValue(Community.class);
                imageView.setImageURI(Uri.parse(community.imageUri));
                textView.setText(community.message);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        FirebaseDatabase.getInstance().getReference("/Comments/"+postId).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Comment newComment = snapshot.getValue(Comment.class);
                comments.add(newComment);
                adapter.notifyItemInserted(adapter.getItemCount()-1);
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

        findViewById(R.id.floatingActionButton4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailCommunityActivity.this, CommentWriteActivity.class);
                intent.putExtra("postId", postId);
                startActivity(intent);
            }
        });
    }

    public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder>{

        @NonNull
        @Override
        public CommentAdapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new CommentAdapter.CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_comment, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull CommentAdapter.CommentViewHolder holder, int position) {
            Comment comment = comments.get(position);

            holder.commentID.setText(comment.getWriteId());
            holder.commentMsg.setText(comment.getMessage());

        }

        @Override
        public int getItemCount() { return comments.size(); }


        class CommentViewHolder extends RecyclerView.ViewHolder {

            TextView commentID, commentMsg;

            public CommentViewHolder(@NonNull View itemView) {
                super(itemView);

                commentID = itemView.findViewById(R.id.commentId);
                commentMsg = itemView.findViewById(R.id.commentText);
            }
        }
    }
}