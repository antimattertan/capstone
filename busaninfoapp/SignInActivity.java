package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;


public class SignInActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;

    private FirebaseAuth mAuth;
    private ImageView userImage;
    private EditText signEmail, signName, signPwd, signPwdCk;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    Uri selectdImageUri;
    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        userImage = findViewById(R.id.userImage);
        signEmail = findViewById(R.id.editTextRegID);
        signPwd = findViewById(R.id.editTextRegPw);
        signName = findViewById(R.id.editTextName);
        signPwdCk = findViewById(R.id.editTextRegPwCk);

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });
        
        findViewById(R.id.signButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signEmail.getText().toString();
                String name = signName.getText().toString();
                String password = signPwd.getText().toString();
                String pwdCheck = signPwdCk.getText().toString();

                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "아이디 또는 이름, 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    if(password.equals(pwdCheck)) {
                        createAccount(email, password, name);
                    }
                    else {
                        Toast.makeText(SignInActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    // 회원가입 메서드
    private void createAccount(String email, String password, String name) {

        // 회원가입을 원하는 사용자의 정보를 전달하여 회원가입하는 부분
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            uploadImage(selectdImageUri);
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignInActivity.this, "" + name + "님의 회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent loginIntent = new Intent(SignInActivity.this, LoginActivity.class);
                            startActivity(loginIntent);
                            finish();
                        } else {
                            Toast.makeText(SignInActivity.this, "회원가입이 실패!!.", Toast.LENGTH_LONG).show();
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
                    .apply(cropOptions.optionalCircleCrop()).into(userImage);

        }
    }

    private void uploadImage(Uri uri) {

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
                        Toast.makeText(getApplicationContext(), "업로드 성공", Toast.LENGTH_LONG).show();
                        Uri downloadUri = task.getResult();

                        User user = new User();
                        user.setUserEmail(signEmail.getText().toString());
                        user.setUserName(signName.getText().toString());
                        user.setUserPassword(signPwd.getText().toString());
                        user.setUserImageUri(downloadUri.toString());
                        DatabaseReference ref = db.getReference();
                        ref.child("users").child(mAuth.getUid()).setValue(user);

                    }
                }
            });
        } catch (NullPointerException e) {
            Toast.makeText(this, "이미지 선택 안함", Toast.LENGTH_LONG).show();
        }
    }
}