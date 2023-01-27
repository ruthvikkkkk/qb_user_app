package com.example.listenupuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.listenupuser.adapter.CartAdapter;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.Order;
import com.example.listenupuser.network.CartApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cart);
        Gson gson = new Gson();

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        SharedPreferences cartPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        CartDto cartDto = gson.fromJson(cartPreferences.getString("session cart", null), CartDto.class);

        RecyclerView recyclerView = findViewById(R.id.rv_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        cartApiInterface.findAllInCart(cartDto.getCartId()).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                List<CartItem> cartItems = response.body();
                int i = 0;
                for(CartItem cartItem : cartItems){
                    cartItem.setImage(cartDto.getCartItems().get(i++).getImage());
                }
                recyclerView.setAdapter(new CartAdapter(cartItems));
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

            }
        });

        findViewById(R.id.bt_cart_place_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    cartPreferences.edit().putString("session cart", gson.toJson(cartDto));
                    cartPreferences.edit().commit();
                    finish();
                }else{
                    Toast.makeText(myApplication, "Please Login!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserCart.this, UserLogin.class));
                }
            }
        });

        findViewById(R.id.bt_cart_delete_all).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartApiInterface.deleteItems(cartDto.getCartId()).enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        cartApiInterface.findAllInCart(cartDto.getCartId()).enqueue(new Callback<List<CartItem>>() {
                            @Override
                            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                                cartDto.setCartItems(response.body());
                                recyclerView.setAdapter(new CartAdapter(cartDto.getCartItems()));
                                cartDto.setCartItems(new ArrayList<>());
                                cartPreferences.edit().putString("session cart", gson.toJson(cartDto));
                                cartPreferences.edit().commit();
                            }

                            @Override
                            public void onFailure(Call<List<CartItem>> call, Throwable t) {

                            }
                        });
                        Toast.makeText(myApplication, "Items Deleted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {

                    }
                });
            }
        });
    }
}