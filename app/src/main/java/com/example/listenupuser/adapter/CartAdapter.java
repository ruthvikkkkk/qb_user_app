package com.example.listenupuser.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.listenupuser.MyApplication;
import com.example.listenupuser.R;
import com.example.listenupuser.UserCart;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.network.CartApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CartAdapter extends RecyclerView.Adapter {

    List<CartItem> cartItems;
    Boolean isOrder;

    public CartAdapter(List<CartItem> cartItems){
        this.cartItems = cartItems;
    }
    public CartAdapter(List<CartItem> cartItems, Boolean isOrder){
        this.cartItems = cartItems;
        this.isOrder = true;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product_card, parent, false);
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
            ((TextView) view.findViewById(R.id.tv_cart_product_merchant)).setText(cartItem.getProductDetails().getMerchantName());
            Glide.with(view.getContext()).load(cartItem.getImage()).into(((ImageView) view.findViewById(R.id.iv_cart_product_image)));
            TextView price = view.findViewById(R.id.tv_cart_product_price);
            price.setText(cartItem.getProductPrice() * cartItem.getQuantity() + "");
            //UserCart.TOTAL_COST += cartItem.getProductPrice() * cartItem.getQuantity();

            ArrayAdapter<String> qtyArrayAdapter;
            Spinner qtySpinner = ((Spinner) view.findViewById(R.id.cart_product_qty_spinner));
            List<String> qtyArray = new ArrayList<>();
            qtyArrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_spinner_dropdown_item, qtyArray);
            qtySpinner.setVisibility(View.VISIBLE);
            qtyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            qtySpinner.setAdapter(qtyArrayAdapter);
            qtyArrayAdapter.notifyDataSetChanged();

            for(int i = 1; i <= cartItem.getQuantity(); i++)
                qtyArray.add(i + "");

            qtyArrayAdapter.notifyDataSetChanged();
            qtySpinner.setSelection(cartItem.getQuantity()-1);

            qtySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    cartItem.setQuantity(qtySpinner.getSelectedItemPosition()+1);
                    price.setText(cartItem.getProductPrice() * (qtySpinner.getSelectedItemPosition()+1) + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//            if(isOrder){
//                qtySpinner.setVisibility(View.INVISIBLE);
//            }
        }
    }
}
