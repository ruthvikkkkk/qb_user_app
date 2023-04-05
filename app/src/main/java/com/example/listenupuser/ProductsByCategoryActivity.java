package com.example.listenupuser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.listenupuser.adapter.HomeProductsAdapter;
import com.example.listenupuser.models.Product;
import com.example.listenupuser.network.ProductApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProductsByCategoryActivity extends AppCompatActivity implements HomeProductsAdapter.IProductCommunicator {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_by_category);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        String category = getIntent().getStringExtra("category");
        ImageView imageView = findViewById(R.id.iv_product_category_category);
        String categoryId = category.toUpperCase().substring(0,3);
        if(category.equals("radio"))
            imageView.setImageDrawable(getDrawable(R.drawable.radio_category));
        else if(category.equals("wired"))
            imageView.setImageDrawable(getDrawable(R.drawable.wired_category));
        else if(category.equals("speaker"))
            imageView.setImageDrawable(getDrawable(R.drawable.speakers_category));
        else if(category.equals("nwired"))
            imageView.setImageDrawable(getDrawable(R.drawable.wireless_icon));
        else if(category.equals("soundbar"))
            imageView.setImageDrawable(getDrawable(R.drawable.soundbar_category));

        RecyclerView recyclerView = findViewById(R.id.rv_product_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit retrofit = myApplication.productRetrofit;
        ProductApiInterface productApiInterface = retrofit.create(ProductApiInterface.class);

        productApiInterface.getByCategory(categoryId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                List<Product> products = response.body();
                recyclerView.setAdapter(new HomeProductsAdapter(products , ProductsByCategoryActivity.this));
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    @Override
    public void openSingleProductDetail(Product product) {
        Intent intent = new Intent(getApplicationContext(), SingleProductDetailsActivity.class);
        intent.putExtra("product", product);
        startActivity(intent);
    }
}