package com.example.test;

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
import com.google.firebase.auth.FirebaseUser;

public class SigninActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText signEmail, signPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        mAuth = FirebaseAuth.getInstance();

        signEmail = findViewById(R.id.editTextRegID);
        signPwd = findViewById(R.id.editTextRegPw);



        findViewById(R.id.signButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = signEmail.getText().toString();
                String password = signPwd.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SigninActivity.this, "아이디 또는 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    createAccount(email, password);
                }
            }
        });
    }
    // 회원가입 메서드
    private void createAccount(String email, String password) {
            // 회원가입을 원하는 사용자의 정보를 전달하여 회원가입하는 부분
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(SigninActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_LONG).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent loginIntent = new Intent(SigninActivity.this, LoginActivity.class);
                                startActivity(loginIntent);
                                finish();
                            } else {
                                Toast.makeText(SigninActivity.this, "회원가입이 실패!!.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
    }
}