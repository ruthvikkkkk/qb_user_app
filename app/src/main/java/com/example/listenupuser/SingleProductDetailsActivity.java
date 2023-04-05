package com.example.listenupuser;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

public class SingleProductDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ArrayAdapter<String> qtyArrayAdapter;
    ArrayAdapter<String> merchantArrayAdapter;
    final String[] stockDetails = {"", "", "", ""};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_product_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit stockRetrofit = myApplication.stockRetrofit;
        StockApiInterface stockApiInterface = stockRetrofit.create(StockApiInterface.class);

        Retrofit cartRetrofit = myApplication.cartRetrofit;
        CartApiInterface cartApiInterface = cartRetrofit.create(CartApiInterface.class);

        Gson gson = new Gson();
        CartDto cart = gson.fromJson(getSharedPreferences("cart", MODE_PRIVATE).getString("session cart", null), CartDto.class);
        SharedPreferences.Editor cartEditor = getSharedPreferences("cart", MODE_PRIVATE).edit();

        Product product = ((Product)getIntent().getSerializableExtra("product"));
        //((TextView) findViewById(R.id.)).setText(product.getProductBrand());
        ((TextView) findViewById(R.id.tv_full_product_name)).setText(product.getProductName());
        ((TextView) findViewById(R.id.tv_full_product_aptx)).setText(product.getAptX());
        ((TextView) findViewById(R.id.tv_full_product_bass)).setText(product.getHighBass());
        ((TextView) findViewById(R.id.tv_full_product_wr)).setText(product.getWaterResistant());
        Glide.with(SingleProductDetailsActivity.this).load(product.getImageURL()).into(((ImageView) findViewById(R.id.iv_full_product_image)));

        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_qty));
        List<String> qtyArray = new ArrayList<>();
        qtyArray.add("Select Quantity:");
        qtyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
        qtySpinner.setVisibility(View.VISIBLE);
        qtyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpinner.setAdapter(qtyArrayAdapter);
        qtyArrayAdapter.notifyDataSetChanged();

        Spinner merchantSpinner = ((Spinner) findViewById(R.id.sp_full_product_merchant));
        List<String> merchantArray = new ArrayList<>();
        //merchantArray.add("Select Merchant");
        merchantArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, merchantArray);
        merchantSpinner.setVisibility(View.VISIBLE);
        merchantArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        merchantSpinner.setAdapter(merchantArrayAdapter);
        merchantArrayAdapter.notifyDataSetChanged();

        merchantSpinner.setOnItemSelectedListener(this);

        stockApiInterface.getByProductId(product.getProductID()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                List<Stock> stockList = response.body();
                Stock stock = stockList.get(merchantSpinner.getSelectedItemPosition()+1);
                ((TextView) findViewById(R.id.tv_full_product_price)).setText(String.valueOf((stock.getPrice())) + " -Rs.");
                ((TextView) findViewById(R.id.tv_full_product_qty)).setText(String.valueOf((stock.getQuantity())));
                stockDetails[0] = stock.getSkuId();
                stockDetails[1] = stock.getMerchant().getMerchantId();
                stockDetails[2] = stock.getMerchant().getName();
                stockDetails[3] = stock.getQuantity()+"";
                for(int i = 1; i <= stock.getQuantity(); i++)
                    qtyArray.add(i + "");

                for(Stock merchantStock : stockList)
                    merchantArray.add(merchantStock.getMerchant().getName());
                qtyArrayAdapter.notifyDataSetChanged();
                merchantArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });

        findViewById(R.id.bt_full_product_add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner qtySpinner = findViewById(R.id.sp_full_product_qty);
                Spinner merchantSpinner = findViewById(R.id.sp_full_product_merchant);

                if(((TextView) findViewById(R.id.tv_full_product_qty)).getText().toString().equals("0")){
                    Toast.makeText(myApplication, "Out of Stock!", Toast.LENGTH_SHORT).show();
                }else{
                    if(qtySpinner.getSelectedItemId() > 0){
                        CartItem cartItem = new CartItem();
                        ProductDetails productDetails = new ProductDetails();
                        cartItem.setProductPrice(Integer.parseInt(String.valueOf(((TextView) findViewById(R.id.tv_full_product_price)).getText()).split(" ")[0]));
                        cartItem.setQuantity(Integer.parseInt(String.valueOf(qtySpinner.getSelectedItemId())));
                        cartItem.setTotalQuantity(Integer.parseInt(stockDetails[3]));
                        cartItem.setSkuID(stockDetails[0]);
                        productDetails.setProductId(product.getProductID());
                        productDetails.setProductName(product.getProductName());
                        productDetails.setMerchantId(stockDetails[1]);
                        productDetails.setMerchantName(stockDetails[2]);
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
                    }else{
                        Toast.makeText(myApplication, "Quantity cannot be 0!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit stockRetrofit = myApplication.stockRetrofit;
        StockApiInterface stockApiInterface = stockRetrofit.create(StockApiInterface.class);

        Product product = ((Product)getIntent().getSerializableExtra("product"));
        ((TextView) findViewById(R.id.tv_full_product_name)).setText(product.getProductName());
        ((TextView) findViewById(R.id.tv_full_product_aptx)).setText(product.getAptX());
        ((TextView) findViewById(R.id.tv_full_product_bass)).setText(product.getHighBass());
        ((TextView) findViewById(R.id.tv_full_product_wr)).setText(product.getWaterResistant());
        Glide.with(SingleProductDetailsActivity.this).load(product.getImageURL()).into(((ImageView) findViewById(R.id.iv_full_product_image)));
        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_qty));
        List<String> qtyArray = new ArrayList<>();
        qtyArray.add("quantity");
        qtyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
        qtySpinner.setVisibility(View.VISIBLE);
        qtyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpinner.setAdapter(qtyArrayAdapter);
        qtyArrayAdapter.notifyDataSetChanged();

        Spinner merchantSpinner = ((Spinner) findViewById(R.id.sp_full_product_merchant));
        List<String> merchantArray = new ArrayList<>();
        merchantArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, merchantArray);
        merchantSpinner.setVisibility(View.VISIBLE);
        merchantArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        merchantSpinner.setAdapter(merchantArrayAdapter);
        merchantArrayAdapter.notifyDataSetChanged();


        stockApiInterface.getByProductId(product.getProductID()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                List<Stock> stockList = response.body();
                Stock stock = stockList.get(merchantSpinner.getSelectedItemPosition()+1);
                ((TextView) findViewById(R.id.tv_full_product_price)).setText(String.valueOf((stock.getPrice())));
                ((TextView) findViewById(R.id.tv_full_product_qty)).setText(String.valueOf((stock.getQuantity())));
                stockDetails[0] = stock.getSkuId();
                stockDetails[1] = stock.getMerchant().getMerchantId();
                stockDetails[2] = stock.getMerchant().getName();
                stockDetails[3] = stock.getQuantity()+"";
                for(int i = 1; i <= stock.getQuantity(); i++)
                    qtyArray.add(i+"");
                for(Stock merchantStock : stockList)
                    merchantArray.add(merchantStock.getMerchant().getName());
                qtyArrayAdapter.notifyDataSetChanged();
                merchantArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Spinner qtySpinner = ((Spinner) findViewById(R.id.sp_full_product_qty));
        List<String> qtyArray = new ArrayList<>();
        qtyArray.add("Select Quantity:");
        qtyArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, qtyArray);
        qtySpinner.setVisibility(View.VISIBLE);
        qtyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtySpinner.setAdapter(qtyArrayAdapter);
        qtyArrayAdapter.notifyDataSetChanged();


        MyApplication myApplication = (MyApplication) getApplication();
        Retrofit stockRetrofit = myApplication.stockRetrofit;
        StockApiInterface stockApiInterface = stockRetrofit.create(StockApiInterface.class);
        Product product = ((Product)getIntent().getSerializableExtra("product"));
        List<String> merchantArray = new ArrayList<>();

        stockApiInterface.getByProductId(product.getProductID()).enqueue(new Callback<List<Stock>>() {
            @Override
            public void onResponse(Call<List<Stock>> call, Response<List<Stock>> response) {
                List<Stock> stockList = response.body();
                Stock stock = stockList.get(position);
                ((TextView) findViewById(R.id.tv_full_product_price)).setText(String.valueOf((stock.getPrice())) + " -Rs.");
                ((TextView) findViewById(R.id.tv_full_product_qty)).setText(String.valueOf((stock.getQuantity())));
                stockDetails[0] = stock.getSkuId();
                stockDetails[1] = stock.getMerchant().getMerchantId();
                stockDetails[2] = stock.getMerchant().getName();
                stockDetails[3] = stock.getQuantity()+"";
                for(int i = 1; i <= stock.getQuantity(); i++)
                    qtyArray.add(i + "");

                for(Stock merchantStock : stockList)
                    merchantArray.add(merchantStock.getMerchant().getName());
                qtyArrayAdapter.notifyDataSetChanged();
                // merchantArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Stock>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}