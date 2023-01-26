package com.example.listenupuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenupuser.R;
import com.example.listenupuser.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter {

    List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_recycle_card, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        ((CartViewHolder) holder).cartBind(cartItem);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{

        View view;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }

        public void cartBind(CartItem cartItem){
            ((TextView) view.findViewById(R.id.tv_cart_product_name)).setText(cartItem.getProductDetails().getProductName());
            ((TextView) view.findViewById(R.id.tv_cart_product_price)).setText(cartItem.getProductPrice()* cartItem.getQuantity() + "");
            ((TextView) view.findViewById(R.id.tv_cart_merchant_name)).setText(cartItem.getProductDetails().getMerchantName());
            ((TextView) view.findViewById(R.id.tv_cart_product_qty)).setText(cartItem.getQuantity()+"");
            Glide.with(view.getContext()).load(cartItem.getImage()).into(((ImageView) view.findViewById(R.id.iv_cart_product_image)));
        }
    }
}
