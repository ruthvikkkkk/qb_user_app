package com.example.listenupuser;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listenupuser.models.Order;
import com.example.listenupuser.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class UserDetails extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference databaseReference;
        if(firebaseUser != null){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);

                    if(user != null){
                        ((TextView) findViewById(R.id.tv_set_customer_name)).setText(user.getUsername());
                        ((TextView) findViewById(R.id.tv_set_address)).setText(user.getAddress());
                        ((TextView) findViewById(R.id.tv_set_email)).setText(user.getEmail());
                        ((TextView) findViewById(R.id.tv_set_phone_number)).setText(user.getMobile());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        findViewById(R.id.button_sign_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSharedPreferences("init");
                finish();
            }
        });
        findViewById(R.id.bt_order_history_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), OrderHistory.class));
            }
        });
    }
}
