package com.example.listenupuser;

import android.app.Application;

import com.example.listenupuser.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApplication extends Application {
    Retrofit productRetrofit;
    Retrofit stockRetrofit;

    Retrofit cartRetrofit;

    Retrofit searchRetrofit;

    static Boolean isGuest = true;
    static String email = "guests";

    @Override
    public void onCreate() {
        super.onCreate();
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder().build();
        productRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.PRODUCT_HOST)
                .build();

        stockRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.STOCK_HOST)
                .build();

        cartRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.CART_HOST)
                .build();

        searchRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.SEARCH_HOST)
                .build();
    }
}
