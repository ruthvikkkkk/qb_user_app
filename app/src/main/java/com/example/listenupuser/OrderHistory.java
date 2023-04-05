package com.example.listenupuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.listenupuser.adapter.CartAdapter;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.CartItemsItem;
import com.example.listenupuser.models.Order;
import com.example.listenupuser.network.CartApiInterface;
import com.example.listenupuser.network.StockApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        RecyclerView recyclerView = findViewById(R.id.rv_order_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        Button orderHistory = findViewById(R.id.bt_order_history);

        if(MyApplication.isGuest){
            orderHistory.setVisibility(View.INVISIBLE);
        }else{
            orderHistory.setVisibility(View.VISIBLE);
        }

        findViewById(R.id.bt_order_history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartApiInterface.orderHistory(MyApplication.email).enqueue(new Callback<List<Order>>() {
                    @Override
                    public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                        List<Order> orders = response.body();
                        List<CartItem> cartItems = new ArrayList<>();
                        for(Order order : orders){
                            for(CartItemsItem cartItemsItem : order.getCartItems()) {
                                CartItem cartItem = new CartItem();
                                cartItem.setProductDetails(cartItemsItem.getProductDetails());
                                cartItem.setQuantity(cartItemsItem.getQuantity());
                                cartItem.setProductPrice(cartItemsItem.getProductPrice());
                                cartItem.setSkuID(cartItemsItem.getSkuID());

                                cartItems.add(cartItem);
                            }
                        }
                        recyclerView.setAdapter(new CartAdapter(cartItems, true));
                    }

                    @Override
                    public void onFailure(Call<List<Order>> call, Throwable t) {

                    }
                });
            }
        });
    }
}