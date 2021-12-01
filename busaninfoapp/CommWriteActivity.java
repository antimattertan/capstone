package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

public class CommWriteActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private FirebaseDatabase firebaseDatabase;
    Community community;
    private EditText msg;
    private String message;
    private String imageUri;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    ArrayList<Uri> uriList = new ArrayList<>();
    RecyclerView recyclerView;
    imageSelectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_write);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("글 작성");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("users");

        recyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                ref2.child(uid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User usr = dataSnapshot.getValue(User.class);
                        community.reviewId = usr.getUserName();
                        community.imageUri = usr.getUserImageUri();
                        community.message = message;
                        community.writeTime = System.currentTimeMillis();
                        //community.imageUri = imageUri;
                        for(int i =0; i < uriList.size(); i++) {
                            community.imgUri.add(uriList.get(i).toString());
                        }
                        community.userUid = user.getUid();
                        community.postId = ref.getKey();
                        ref.setValue(community);
                        //Uri image = Uri.parse(imageUri);
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

        if (data == null) {
            Toast.makeText(getApplicationContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
        } else {
            if(data.getClipData() == null) {
                Uri imageUri = data.getData();
                uriList.add(imageUri);

                adapter = new imageSelectAdapter(uriList, getApplicationContext());

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

            } else {
                ClipData clipData = data.getClipData();

                if(clipData.getItemCount() > 10) {
                    Toast.makeText(getApplicationContext(), "10장까지 선택이 가능합니다.", Toast.LENGTH_LONG).show();
                } else {
                    for(int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageuri = clipData.getItemAt(i).getUri();
                        try {
                            uriList.add(imageuri);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    adapter = new imageSelectAdapter(uriList, getApplicationContext());

                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

                }
            }
        }
    }
}