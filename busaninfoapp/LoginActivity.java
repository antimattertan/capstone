package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edEmail, edPwd;
    public String name;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // 회원가입 화면 진입 인텐드
        Intent signIntent = new Intent(LoginActivity.this, SignInActivity.class);

        getSupportActionBar().setTitle("부산 관광 정보앱");

        edEmail = findViewById(R.id.editTextID);
        edPwd = findViewById(R.id.editTextPassword);


        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edEmail.getText().toString();
                String pwd = edPwd.getText().toString();
                login(email, pwd);
            }
        });

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signIntent);
            }
        });
    }

    // 로그인 메서드
    private void login(String email, String password) {
        ref = FirebaseDatabase.getInstance().getReference();
        Intent mainIntent = new Intent(LoginActivity.this, CategoryActivity.class);
        // 사용자의 로그인 정보를 확인하여 로그인 하는 부분
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            ref.child("users").child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    name = user.getUserName();
                                    // Sign in success, update UI with the signed-in user's information
                                    Toast.makeText(LoginActivity.this, "" + name + "님  환영합니다.", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                            //FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(mainIntent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}