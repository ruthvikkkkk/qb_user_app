package com.example.listenupuser.network;

import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApiInterface {

    @POST("createCart/")
    Call<CartDto> createCart(@Body CartDto cartDto);

    @POST("addToCart/{cartId}")
    Call<List<CartItem>> addToCart(@Path("cartId") String cartId, @Body CartItem cartItem);

    @GET("findAllInCart/{cartId}")
    Call<List<CartItem>> findAllInCart(@Path("cartId") String cartId);

    @POST("buy/{cartId}")
    Call<Order> buyCart(@Path("cartId") String cartId);

    @DELETE("deleteAllCartItems/{id}")
    Call<Boolean> deleteItems(@Path("id") String cartId);

    @DELETE("deleteCart/{id}")
    Call<String> deleteCart(@Path("id") String cartId);

    @GET("history/{email}")
    Call<List<Order>> orderHistory(@Path("email") String email);
}
