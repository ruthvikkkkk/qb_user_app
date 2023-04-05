package com.example.listenupuser.network;

import com.example.listenupuser.models.Product;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SearchApiInterface {
    @GET("getItems/{search}")
    Call<List<Product>> searchItems(@Path("search") String searchTerm);
}