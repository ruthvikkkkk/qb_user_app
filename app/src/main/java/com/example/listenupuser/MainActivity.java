package com.example.listenupuser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.listenupuser.adapter.HomeProductsAdapter;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.Customer;
import com.example.listenupuser.models.Product;
import com.example.listenupuser.network.CartApiInterface;
import com.example.listenupuser.network.ProductApiInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity implements HomeProductsAdapter.IProductCommunicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //10.20.5.8:8089
        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit productRetrofit = myApplication.productRetrofit;
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        ProductApiInterface productApiInterface = productRetrofit.create(ProductApiInterface.class);
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        CartDto cartDto = new CartDto();
        cartDto.setCartItems(new ArrayList<CartItem>());
        cartDto.setCustomer(new Customer());
        cartDto.setGuest(true);
        SharedPreferences cartPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        SharedPreferences.Editor cartEditor = cartPreferences.edit();
        cartEditor.commit();
        Gson gson = new Gson();

        if(cartPreferences.getString("session cart", null) == null) {
            cartApiInterface.createCart(cartDto).enqueue(new Callback<CartDto>() {
                @Override
                public void onResponse(Call<CartDto> call, Response<CartDto> response) {
                    CartDto cartResponseDto = response.body();
                    cartEditor.putString("session cart", gson.toJson(cartResponseDto));
                    cartEditor.commit();
                    //Log.i("cart id" ,cartResponseDto.getCartId());
                }

                @Override
                public void onFailure(Call<CartDto> call, Throwable t) {

                }
            });
        }

        RecyclerView recyclerView = findViewById(R.id.rv_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));

        productApiInterface.getAll().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<Product> products = response.body();
                    Log.i("products", products.toString());
                    recyclerView.setAdapter(new HomeProductsAdapter(products, MainActivity.this));

                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i("failure", t.getLocalizedMessage());
            }
        });

        //recyclerView.smoothScrollToPosition(recyclerView.getAdapter().getItemCount());
        Intent intent = new Intent(MainActivity.this, ProductsByCategoryActivity.class);
        findViewById(R.id.iv_home_radio).setOnClickListener(v -> {
            intent.putExtra("category", "radio");
            startActivity(intent);
        });

        findViewById(R.id.iv_home_wired).setOnClickListener(v -> {
            intent.putExtra("category", "wired");
            startActivity(intent);
        });

        findViewById(R.id.iv_home_speaker).setOnClickListener(v -> {
            intent.putExtra("category", "speaker");
            startActivity(intent);
        });

        findViewById(R.id.iv_home_wireless).setOnClickListener(v -> {
            intent.putExtra("category", "nwired");
            startActivity(intent);
        });

        findViewById(R.id.iv_home_soundbar).setOnClickListener(v -> {
            intent.putExtra("category", "soundbar");
            startActivity(intent);
        });
    }

    @Override
    public void openSingleProductDetail(Product product) {
        Intent intent = new Intent(getApplicationContext(), SingleProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}