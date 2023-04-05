package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotActivity extends AppCompatActivity {

    private EditText etMail;
    private Button resetPassword;

    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        etMail=findViewById(R.id.et_forgot_email);
        resetPassword=findViewById(R.id.button_reset_password);
        //progressBar=findViewById(R.id.progressBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        auth=FirebaseAuth.getInstance();

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }
    public void resetPassword(){
        String email=etMail.getText().toString().trim();
        if(email.isEmpty()){
            etMail.setError("Email is required");
            etMail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMail.setError("Please valid mail address");
            etMail.requestFocus();
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotActivity.this, "Check the mail to reset your password", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotActivity.this, "Something went wrong, Try again!", Toast.LENGTH_LONG ).show();
                }
            }
        });

    }
}