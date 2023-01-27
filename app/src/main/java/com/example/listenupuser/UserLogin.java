package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserLogin extends AppCompatActivity {

    private EditText etMail;
    private EditText etPass;
    private Button login;
    private TextView signUp;
    private TextView forgot;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        etMail = findViewById(R.id.et_sign_in_email);
        etPass = findViewById(R.id.et_sign_in_password);
        login = findViewById(R.id.bt_sign_in_sign_in);
        forgot = findViewById(R.id.tv_sign_in_forgot);
        signUp = findViewById(R.id.tv_sign_in_sign_up);
        firebaseAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void userLogin(){
        String email=etMail.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        if(email.isEmpty()){
            etMail.setError("Mail is required");
            etMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPass.setError("Password is required");
            etPass.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMail.setError("Please provide a Valid email");
            etMail.requestFocus();
            return;
        }
        if(password.length() < 8){
            etPass.setError("Password should be atleast 8 characters");
            etPass.requestFocus();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    SharedPreferences sharedPreferences = getSharedPreferences("init", MODE_PRIVATE);
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("email", email);
                    edit.putBoolean("isGuest", false);
                    edit.commit();
                    Intent intent=new Intent(UserLogin.this, MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(UserLogin.this, "Login Failed! Please check credentails", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}