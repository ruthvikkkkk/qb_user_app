package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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

        MyApplication.isGuest = getSharedPreferences("init", MODE_PRIVATE).getBoolean("isGuest", true);
        MyApplication.email = getSharedPreferences("init", MODE_PRIVATE).getString("email", "");

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit productRetrofit = myApplication.productRetrofit;
        ProductApiInterface productApiInterface = productRetrofit.create(ProductApiInterface.class);

        ActionBar actionBar = getSupportActionBar();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        MenuItem userItem = menu.findItem(R.id.user);
        if(!MyApplication.isGuest){
            userItem.setIcon(getDrawable(R.drawable.user_in));
        }else{
            userItem.setIcon(getDrawable(R.drawable.user_out));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.cart){
            startActivity(new Intent(MainActivity.this, UserCart.class));
        }
        else if(item.getItemId() == R.id.user){
            if(item.getIcon().getConstantState().equals(getDrawable(R.drawable.user_out).getConstantState())) {
                startActivity(new Intent(MainActivity.this, UserLogin.class));
                //createCart(false);
            }else{
                MyApplication.isGuest = true;
                MyApplication.email = "";
                //createCart();
                deleteSharedPreferences("cart");
                deleteSharedPreferences("init");
                CartApiInterface cartApiInterface = ((MyApplication) getApplication()).cartRetrofit.create(CartApiInterface.class);
                cartApiInterface.deleteCart(MyApplication.email).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "cart deleted!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                startActivity(new Intent(MainActivity.this, UserLogin.class));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //createCart();
        if(getSharedPreferences("init", MODE_PRIVATE).getAll().size() > 0) {
            findViewById(R.id.user).setBackground(getDrawable(R.drawable.user_in));
            findViewById(R.id.user).setForeground(getDrawable(R.drawable.user_in));
        }
        else {
            findViewById(R.id.user).setForeground(getDrawable(R.drawable.user_out));
            findViewById(R.id.user).setBackground(getDrawable(R.drawable.user_out));
            //createCart();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        createCart();
    }

    public void createCart(){

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        Gson gson = new Gson();
        SharedPreferences cartPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        SharedPreferences.Editor cartEditor = cartPreferences.edit();

        if(!MyApplication.isGuest) {
            CartDto cartDto = new CartDto();
            cartDto = gson.fromJson(cartPreferences.getString("session cart", null), CartDto.class);
            cartDto.setGuest(false);
            cartDto.setCartId(MyApplication.email);
            cartDto.getCustomer().setEmail(MyApplication.email);
            cartApiInterface.createCart(cartDto).enqueue(new Callback<CartDto>() {
                @Override
                public void onResponse(Call<CartDto> call, Response<CartDto> response) {
                    //CartDto cartDto = new CartDto();
                    CartDto cartResponseDto = response.body();
                    //MyApplication.email = cartResponseDto.getCartId();
                    Log.i("user cart", cartResponseDto.toString());
                }

                @Override
                public void onFailure(Call<CartDto> call, Throwable t) {

                }
            });
            cartEditor.putString("session cart", gson.toJson(cartDto));
        }else{
            if(cartPreferences.getAll().size() == 0) {
                CartDto cartDto = new CartDto();
                cartDto.setCustomer(new Customer());
                cartDto.setCartItems(new ArrayList<CartItem>());
                cartDto.setGuest(true);
                cartApiInterface.createCart(cartDto).enqueue(new Callback<CartDto>() {
                    @Override
                    public void onResponse(Call<CartDto> call, Response<CartDto> response) {
                        CartDto guestCart = response.body();
                        cartEditor.putString("session cart", gson.toJson(guestCart));
                        cartEditor.commit();
                    }

                    @Override
                    public void onFailure(Call<CartDto> call, Throwable t) {

                    }
                });
            }else{

            }

            //customer.setEmail(MyApplication.email);
            //cartDto.setCustomer(customer);
        }

        cartEditor.commit();
    }
}