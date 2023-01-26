package com.example.listenupuser.network;

import com.example.listenupuser.models.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApiInterface {
    @GET("getall")
    Call<List<Product>> getAll();

    @GET("get/category/{id}")
    Call<List<Product>> getByCategory(@Path("id") String categoryId);
}
