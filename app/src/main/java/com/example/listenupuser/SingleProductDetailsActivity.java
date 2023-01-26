package com.example.listenupuser;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.listenupuser.models.CartDto;
import com.example.listenupuser.models.CartItem;
import com.example.listenupuser.models.Product;
import com.example.listenupuser.models.ProductDetails;
import com.example.listenupuser.models.Stock;
import com.example.listenupuser.network.CartApiInterface;
import com.example.listenupuser.network.StockApiInterface;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SingleProductDetailsActivity extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_product_details);

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit stockRetrofit = myApplication.stockRetrofit;
        StockApiInterface stockApiInterface = stockRetrofit.create(StockApiInterface.class);

        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        Gson gson = new Gson();
        CartDto cart = gson.fromJson(getSharedPreferences("cart", MODE_PRIVATE).getString("session cart", null), CartDto.class);
        SharedPreferences.Editor cartEditor = getSharedPreferences("cart", MODE_PRIVATE).edit();

        final String[] skuId = {"", "", ""};
        Product product = ((Product)getIntent().getSerializableExtra("product"));
        ((TextView) findViewById(R.id.tv_full_product_brand)).setText(product.getProductBrand());
        ((TextView) findViewById(R.id.tv_full_product_name)).setText(product.getProductName());
        Glide.with(SingleProductDetailsActivity.this).load(product.getImageURL()).into(((ImageView) findViewById(R.id.iv_full_product_image)));
        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_quantity));
        List<String> qtyArray = new ArrayList<>();
        qtyArray.add("quantity");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
        qtySpinner.setVisibility(View.VISIBLE);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        stockApiInterface.getByProductId(product.getProductID()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                List<Stock> stockList = response.body();
                Stock stock = stockList.get(0);
                ((TextView) findViewById(R.id.tv_full_product_price)).setText(String.valueOf((stock.getPrice())));
                ((TextView) findViewById(R.id.tv_full_product_qty)).setText(String.valueOf((stock.getQuantity())));
                ((TextView) findViewById(R.id.tv_full_product_merchant_name)).setText(String.valueOf((stock.getMerchant().getName())));
                skuId[0] += stock.getSkuId();
                skuId[1] += stock.getMerchant().getMerchantId();
                skuId[2] += stock.getMerchant().getName();
                for(int i = 1; i <= stock.getQuantity(); i++)
                    qtyArray.add(i+"");
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });

        findViewById(R.id.bt_full_product_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartItem cartItem = new CartItem();
                ProductDetails productDetails = new ProductDetails();
                Spinner spinner = findViewById(R.id.sp_full_product_quantity);
                cartItem.setProductPrice(Integer.parseInt(String.valueOf(((TextView) findViewById(R.id.tv_full_product_price)).getText())));
                cartItem.setQuantity(Integer.parseInt(String.valueOf(spinner.getSelectedItemId())));
                cartItem.setSkuID(skuId[0]);
                productDetails.setProductId(product.getProductID());
                productDetails.setProductName(product.getProductName());
                productDetails.setMerchantId(skuId[1]);
                productDetails.setMerchantName(skuId[2]);
                cartItem.setProductDetails(productDetails);

                cartItem.setImage(product.getImageURL());
                cart.getCartItems().add(cartItem);
                cartEditor.putString("session cart", gson.toJson(cart));
                cartEditor.commit();
                cartApiInterface.addToCart(cart.getCartId() ,cartItem).enqueue(new Callback<List<CartItem>>() {
                    @Override
                    public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                        List<CartItem> cartItems = response.body();
//                        Log.i("cart items", cartItems.toString());
                    }

                    @Override
                    public void onFailure(Call<List<CartItem>> call, Throwable t) {

                    }
                });
                //Intent intent = new Intent(getApplicationContext(), UserCart.class);
                startActivity(new Intent(getApplicationContext(), UserCart.class));
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        int newQty = 0;
        TextView qty = (TextView) findViewById(R.id.tv_full_product_qty);
        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
//        CartDto cartDto = new Gson().fromJson(sharedPreferences.getString("session cart", null), CartDto.class);
//        for(CartItem cartItem : cartDto.getCartItems()){
//            if(cartItem.getProductDetails().getProductName().equals(String.valueOf(((TextView) findViewById(R.id.tv_full_product_name)).getText()))){
//                newQty = cartItem.getQuantity();
//            }
//        }
//
//        int oldQty = Integer.parseInt(qty.getText().toString());
//        qty.setText(oldQty-newQty+"");
//
//        List<String> qtyArray = new ArrayList<>();
//        qtyArray.add("quantity");
//        for(int i = 1; i <= (oldQty-newQty); i++)
//            qtyArray.add(i+"");
//
//        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_quantity));
//        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
//        qtySpinner.setVisibility(View.VISIBLE);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        qtySpinner.setAdapter(arrayAdapter);
//        arrayAdapter.notifyDataSetChanged();

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit stockRetrofit = myApplication.stockRetrofit;
        StockApiInterface stockApiInterface = stockRetrofit.create(StockApiInterface.class);

        final String[] skuId = {"", "", ""};
        Product product = ((Product)getIntent().getSerializableExtra("product"));
        ((TextView) findViewById(R.id.tv_full_product_brand)).setText(product.getProductBrand());
        ((TextView) findViewById(R.id.tv_full_product_name)).setText(product.getProductName());
        Glide.with(SingleProductDetailsActivity.this).load(product.getImageURL()).into(((ImageView) findViewById(R.id.iv_full_product_image)));
        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_quantity));
        List<String> qtyArray = new ArrayList<>();
        qtyArray.add("quantity");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
        qtySpinner.setVisibility(View.VISIBLE);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpinner.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();

        stockApiInterface.getByProductId(product.getProductID()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                List<Stock> stockList = response.body();
                Stock stock = stockList.get(0);
                ((TextView) findViewById(R.id.tv_full_product_price)).setText(String.valueOf((stock.getPrice())));
                ((TextView) findViewById(R.id.tv_full_product_qty)).setText(String.valueOf((stock.getQuantity())));
                ((TextView) findViewById(R.id.tv_full_product_merchant_name)).setText(String.valueOf((stock.getMerchant().getName())));
                skuId[0] += stock.getSkuId();
                skuId[1] += stock.getMerchant().getMerchantId();
                skuId[2] += stock.getMerchant().getName();
                for(int i = 1; i <= stock.getQuantity(); i++)
                    qtyArray.add(i+"");
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }
}