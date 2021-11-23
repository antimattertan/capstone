package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommWriteActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;
    private FirebaseDatabase firebaseDatabase;
    Community community;
    private EditText msg;
    private String message;
    private String imageUri;
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_write);

        getSupportActionBar().setTitle("글 작성");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");

        imageView = findViewById(R.id.imageUpload);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference("community").push();

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = findViewById(R.id.messageText);
                message = msg.getText().toString();
                community = new Community();

                String uid = user.getUid();
                ref2.child(uid).child("userName").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        community.reviewId = dataSnapshot.getValue(String.class);
                        community.message = message;
                        community.writeTime = System.currentTimeMillis();
                        community.imageUri = imageUri;
                        community.userUid = user.getUid();
                        community.postId = ref.getKey();
                        ref.setValue(community);
                        Uri image = Uri.parse(imageUri);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                Intent intent = new Intent(CommWriteActivity.this, MainCommunityActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri selectdImageUri = data.getData();
            imageUri = selectdImageUri.toString();
            imageView.setImageURI(selectdImageUri);
        }
    }
}