package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listenupuser.adapter.CartAdapter;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.CartItemsItem;
import com.example.listenupuser.models.Order;
import com.example.listenupuser.network.CartApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserCart extends AppCompatActivity {

    public static Double TOTAL_COST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_cart);
        Gson gson = new Gson();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        TOTAL_COST = 0.0;
        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        SharedPreferences cartPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        CartDto cartDto = gson.fromJson(cartPreferences.getString("session cart", null), CartDto.class);

        if(cartDto.getCartItems().size() > 0){
            setContentView(R.layout.activity_user_cart);
            RecyclerView recyclerView = findViewById(R.id.rv_cart);
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));


            //((TextView) findViewById(R.id.tv_cart_total_cost)).setText(TOTAL_COST+"");

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

                        startActivity(new Intent(getApplicationContext(), CheckoutPage.class));
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
                            cartDto.setCartItems(new ArrayList<>());
                            cartPreferences.edit().putString("session cart", gson.toJson(cartDto)).commit();
                            recyclerView.setAdapter(new CartAdapter(cartDto.getCartItems()));;
                            Toast.makeText(myApplication, "Items Deleted!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Boolean> call, Throwable t) {

                        }
                    });
                }
            });

        }else{
            setContentView(R.layout.activity_empty_cart);
        }

//        Button orderHistory = findViewById(R.id.bt_cart_history);
//        if(MyApplication.isGuest){
//            orderHistory.setVisibility(View.INVISIBLE);
//        }else{
//            orderHistory.setVisibility(View.VISIBLE);
//        }
//        findViewById(R.id.bt_cart_history).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cartApiInterface.orderHistory(MyApplication.email).enqueue(new Callback<List<Order>>() {
//                    @Override
//                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                        List<Order> orders = response.body();
//                        List<CartItem> cartItems = new ArrayList<>();
//                        for(Order order : orders){
//                            for(CartItemsItem cartItemsItem : order.getCartItems()) {
//                                CartItem cartItem = new CartItem();
//                                cartItem.setProductDetails(cartItemsItem.getProductDetails());
//                                cartItem.setQuantity(cartItemsItem.getQuantity());
//                                cartItem.setProductPrice(cartItemsItem.getProductPrice());
//                                cartItem.setSkuID(cartItemsItem.getSkuID());
//
//                                cartItems.add(cartItem);
//                            }
//                        }
//                        recyclerView.setAdapter(new CartAdapter(cartItems));
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<Order>> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
        //((TextView) findViewById(R.id.tv_cart_total_cost)).setText(TOTAL_COST+"");

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_bar);
        navigationView.setSelectedItemId(R.id.item_cart_icon);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.item_cart_icon:
                        return true;
                    case R.id.item_home_icon:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }
}