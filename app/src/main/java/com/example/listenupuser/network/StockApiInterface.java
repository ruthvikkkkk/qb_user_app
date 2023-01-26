package com.example.listenupuser.network;

import com.example.listenupuser.models.Stock;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StockApiInterface {
    @GET("viewByProductId/{productId}")
    Call<List<Stock>> getByProductId(@Path("productId") String productId);


}
