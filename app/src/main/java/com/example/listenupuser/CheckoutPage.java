package com.example.listenupuser;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listenupuser.adapter.CartAdapter;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.Order;
import com.example.listenupuser.models.User;
import com.example.listenupuser.network.CartApiInterface;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CheckoutPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_out_page);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);
        Gson gson = new Gson();

        SharedPreferences cartPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        CartDto cartDto = gson.fromJson(cartPreferences.getString("session cart", null), CartDto.class);

        RecyclerView recyclerView = findViewById(R.id.rv_checkout);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new CartAdapter(cartDto.getCartItems()));

        findViewById(R.id.bt_checkout_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(myApplication, "Order Placed", Toast.LENGTH_SHORT).show();
                if (!getSharedPreferences("init", MODE_PRIVATE).getBoolean("isGuest", true)) {
                    cartApiInterface.buyCart(MyApplication.email).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            Toast.makeText(myApplication, "Order Placed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });
                    cartDto.setCartItems(new ArrayList<>());
                    cartPreferences.edit().putString("session cart", gson.toJson(cartDto)).commit();
                    finish();
                }else{
                    Toast.makeText(myApplication, "Please Login!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UserLogin.class));
                }
            }
        });

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
                        ((TextView) findViewById(R.id.tv_checkout_name)).setText(user.getUsername());
                        ((TextView) findViewById(R.id.tv_checkout_phone)).setText(user.getMobile());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }


    }
}
