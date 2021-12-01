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

public class userInfoActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;

    private FirebaseAuth mAuth;

    private ImageView imageView;
    private EditText name, pwd, newPwd, newPwdChk;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseStorage firebaseStorage;
    Uri selectdImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("회원 정보 수정");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();

        imageView = findViewById(R.id.userInfoPhoto);
        name = findViewById(R.id.userInfoName);
        pwd = findViewById(R.id.passwordET);
        newPwd = findViewById(R.id.newPwdET);
        newPwdChk = findViewById(R.id.newPwdChk);

        DatabaseReference userRef = db.getReference("/users");

        userRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                name.setText(user1.getUserName());
                Uri uri = Uri.parse(user1.getUserImageUri());
                RequestOptions cropOptions = new RequestOptions();
                Glide.with(getApplicationContext()).load(uri).apply(cropOptions.optionalCircleCrop()).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        findViewById(R.id.photobutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updateName = name.getText().toString();
                String userPwd = pwd.getText().toString();
                String updatePwd = newPwd.getText().toString();
                String updatePwdCk = newPwdChk.getText().toString();

                if(!(updateName.isEmpty()) && userPwd.isEmpty()) {
                    userRef.child(user.getUid()).child("userName").setValue(updateName);
                }
                else {
                    if(!(updateName.isEmpty()) && !(userPwd.isEmpty())) {
                        userRef.child(user.getUid()).child("userPassword").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String pw = dataSnapshot.getValue(String.class);
                                if(!(userPwd.equals(pw))) {
                                    Toast.makeText(getApplicationContext(), "현재 비밀번호가 일치 하지 않습니다.", Toast.LENGTH_LONG).show();
                                }
                                else {
                                    if(!(updatePwd.equals(updatePwdCk))) {
                                        Toast.makeText(getApplicationContext(), "새로운 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        changePassword(user, updatePwd);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectdImageUri = data.getData();
            RequestOptions cropOptions = new RequestOptions();
            Glide.with(getApplicationContext()).load(selectdImageUri)
                    .apply(cropOptions.optionalCircleCrop()).into(imageView);
            changeImage(selectdImageUri);
        }
    }

    private void changePassword(FirebaseUser user, String password) {

        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "비밀번호가 새로 설정되었습니다.", Toast.LENGTH_LONG).show();
                    DatabaseReference ref = db.getReference();
                    ref.child("users").child(user.getUid()).child("userPassword").setValue(password);
                }
            }
        });
    }
    private void changeImage(Uri uri) {
        try {
            StorageReference storageReference = firebaseStorage.getReference();
            StorageReference riversRef = storageReference.child("images/"+uri.getLastPathSegment());
            UploadTask uploadTask = riversRef.putFile(uri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()) {
                        throw task.getException();
                    }

                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if(task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        DatabaseReference ref = db.getReference();
                        ref.child("users").child(mAuth.getUid()).child("userImageUri").setValue(downloadUri.toString());

                    }
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}