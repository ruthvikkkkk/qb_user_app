package com.example.listenupuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.listenupuser.adapter.HomeProductsAdapter;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.Customer;
import com.example.listenupuser.models.Product;
import com.example.listenupuser.network.CartApiInterface;
import com.example.listenupuser.network.ProductApiInterface;
import com.example.listenupuser.network.SearchApiInterface;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
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
        ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();
        actionBar.setDisplayShowTitleEnabled(false);
        createCart();

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit productRetrofit = myApplication.productRetrofit;
        ProductApiInterface productApiInterface = productRetrofit.create(ProductApiInterface.class);

        RecyclerView recyclerView = findViewById(R.id.rv_home);

        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/I/81H-mS3Y1pL._SX679_.jpg", "Radios", null));
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/I/718ArKRWI3S._SY879_.jpg", "Speakers", null));
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/I/61+3f5q7T6S._SX679_.jpg", "Wireless", null));
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/I/61BsLMD3u2S._SX679_.jpg", "Wired", null));
        slideModels.add(new SlideModel("https://m.media-amazon.com/images/I/41XOA-lcsCL._SX300_SY300_QL70_FMwebp_.jpg", "Sound Bar", null));

        imageSlider.setImageList(slideModels);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                Intent intent = new Intent(MainActivity.this, ProductsByCategoryActivity.class);

                switch (i){
                    case 0:
                        intent.putExtra("category", "radio");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("category", "speaker");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("category", "nwired");
                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("category", "wired");
                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("category", "soundbar");
                        startActivity(intent);
                        break;

                }
            }
        });

        MyApplication.isGuest = getSharedPreferences("init", MODE_PRIVATE).getBoolean("isGuest", true);
        MyApplication.email = getSharedPreferences("init", MODE_PRIVATE).getString("email", "");

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

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

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation_bar);
        navigationView.setSelectedItemId(R.id.item_home_icon);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.item_cart_icon:
                        startActivity(new Intent(getApplicationContext(),UserCart.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item_home_icon:
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void openSingleProductDetail(Product product) {
        Intent intent = new Intent(getApplicationContext(), SingleProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        createCart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_bar, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search_bar);
        MenuItem loggedInItem = menu.findItem(R.id.user_logged_in_icon);
        MenuItem loggedOutItem = menu.findItem(R.id.user_logged_out_icon);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProducts(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchProducts(newText);
                return false;
            }
        });

        if(getSharedPreferences("init", MODE_PRIVATE).getAll().size() > 0) {
            //actionBar.getCustomView().findViewById(R.id.user_logged_out_icon).setVisibility(View.INVISIBLE);
            loggedOutItem.setVisible(false);
        }
        else {
            //actionBar.getCustomView().findViewById(R.id.user_logged_in_icon).setVisibility(View.INVISIBLE);
            loggedInItem.setVisible(false);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.user_logged_out_icon){
            startActivity(new Intent(MainActivity.this, UserLogin.class));
            return true;
        }else if(item.getItemId() == R.id.user_logged_in_icon){
//            deleteSharedPreferences("init");
//            finish();
            startActivity(new Intent(getApplicationContext(), UserDetails.class));
            return true;
        }
        return false;
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

    public List<Product> searchProducts(String searchQuery){


        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit searchRetrofit = myApplication.searchRetrofit;
        SearchApiInterface searchApiInterface = searchRetrofit.create(SearchApiInterface.class);

        RecyclerView recyclerView = findViewById(R.id.rv_home);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        List<Product> products = new ArrayList<>();
        if(searchQuery.equals(""))
            searchQuery = "bass";
        searchApiInterface.searchItems(searchQuery).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful() && response.body() != null){
                    products.addAll(response.body());
                    Log.i("products", products.toString());
                    recyclerView.setAdapter(new HomeProductsAdapter(products, MainActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i("failure", t.getLocalizedMessage());
            }
        });

        return products;
    }


}