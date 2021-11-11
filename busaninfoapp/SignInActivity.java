package com.example.busaninfoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignInActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText signEmail, signName, signPwd, signPwdCk;
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mAuth = FirebaseAuth.getInstance();



        signEmail = findViewById(R.id.editTextRegID);
        signPwd = findViewById(R.id.editTextRegPw);
        signName = findViewById(R.id.editTextName);
        signPwdCk = findViewById(R.id.editTextRegPwCk);


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

        User user = new User();
        user.setUserEmail(email);
        user.setUserName(name);
        user.setUserPassword(password);

        DatabaseReference ref = db.getReference("user").push();
        // 회원가입을 원하는 사용자의 정보를 전달하여 회원가입하는 부분
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            ref.setValue(user);
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
}