package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class CommentWriteActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_write);

        //getSupportActionBar().setTitle("댓글 작성");

        String postId = getIntent().getStringExtra("postId");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

        findViewById(R.id.comment_button).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView textView = findViewById(R.id.comment_write);
                String message = textView.getText().toString();
                if (message.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "댓글을 작성해주세요.", Toast.LENGTH_LONG).show();
                } else {
                    String uid = user.getUid();
                    Comment comment = new Comment();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Comments/"+postId).push();
                    userRef.child(uid).child("userName").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            comment.commentId = ref.getKey();
                            comment.postId = postId;
                            comment.writeId = dataSnapshot.getValue(String.class);
                            comment.writeTime = System.currentTimeMillis();
                            comment.message = textView.getText().toString();
                            onCommentCnt(FirebaseDatabase.getInstance().getReference("/community/" + postId));
                            ref.setValue(comment);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });

    }
    public void onCommentCnt(DatabaseReference communityRef) {
        communityRef.runTransaction(new Transaction.Handler() {
            @NonNull
            @Override
            public Transaction.Result doTransaction(@NonNull MutableData currentData) {
                Community community = currentData.getValue(Community.class);
                if (community == null) {
                    return Transaction.success(currentData);
                } else {
                    community.setCommentCnt(community.getCommentCnt()+1);
                }
                currentData.setValue(community);
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {

            }
        });
    }
}