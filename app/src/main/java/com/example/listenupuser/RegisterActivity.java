package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listenupuser.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;

public class RegisterActivity extends AppCompatActivity {

    private EditText etMail;
    private EditText etPass;
    private EditText etPhone;
    private EditText etAddress;
    private EditText etCity;
    private EditText etState;
    private EditText etName;
    private Button btRegister;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        etMail = findViewById(R.id.et_register_email);
        etPass = findViewById(R.id.et_register_pass);
        etPhone = findViewById(R.id.et_register_phone);
        etAddress = findViewById(R.id.et_register_address);
        etCity = findViewById(R.id.et_register_city);
        etState = findViewById(R.id.et_register_state);
        etName = findViewById(R.id.et_register_name);
        btRegister = findViewById(R.id.bt_register_register);
        firebaseAuth = FirebaseAuth.getInstance();

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    public void registerUser(){
        String email = etMail.getText().toString().trim();
        String password = etPass.getText().toString().trim();
        String phoneNo = etPhone.getText().toString().trim();
        String username = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String state = etState.getText().toString().trim();

        if(email.isEmpty()){
            etMail.setError("Mail is required");
            etMail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPass.setError("Set the Password");
            etPass.requestFocus();
            return;
        }
        if(password.length()<8){
            etPass.setError("Password should be 8 characters atleast");
            etPass.requestFocus();
            return;
        }

        if(phoneNo.isEmpty()){
            etPhone.setError("Mobile no is required");
            etPhone.requestFocus();
            return;
        }

        if(address.isEmpty()){
            etMail.setError("Address is required");
            etMail.requestFocus();
            return;
        }

        if(city.isEmpty()){
            etMail.setError("City is required");
            etMail.requestFocus();
            return;
        }

        if(username.isEmpty()){
            etMail.setError("Username is required");
            etMail.requestFocus();
            return;
        }

        if(state.isEmpty()){
            etMail.setError("State is required");
            etMail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etMail.setError("Please provide Valid Mail");
            etMail.requestFocus();
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user = new User(email, password, username, phoneNo, address, city, state);
                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            FirebaseUser user1 = FirebaseAuth.getInstance().getCurrentUser();
                                            user1.sendEmailVerification();
                                            Toast.makeText(RegisterActivity.this,"Verify the mail to make Registration successful",Toast.LENGTH_LONG).show();
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(RegisterActivity.this,"Registration failed,Try again",Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"Registration failed!",Toast.LENGTH_LONG).show();
                    }
                }
            });

    }
    private void goBack() {
        finish();
    }
}