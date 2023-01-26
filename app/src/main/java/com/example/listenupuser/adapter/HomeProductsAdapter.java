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
import com.example.listenupuser.models.Product;

import java.util.List;

public class HomeProductsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Product> products;

    IProductCommunicator communicator;

    public HomeProductsAdapter(List<Product> productList, IProductCommunicator productCommunicator){
        this.products = productList;
        this.communicator = productCommunicator;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_recycler, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Product product = products.get(position);
        ((ProductViewHolder) holder).productBind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        View view;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    communicator.openSingleProductDetail(products.get(getAdapterPosition()));
                }
            });
        }

        public void productBind(Product product){
            ((TextView) view.findViewById(R.id.tv_home_product_name)).setText(product.getCreatedBy().toString());
            ((TextView) view.findViewById(R.id.tv_home_product_category)).setText(product.getProductName());
            Glide.with(view.getContext()).load(product.getImageURL()).into(((ImageView) view.findViewById(R.id.iv_home_product_image)));
        }
    }

    public interface IProductCommunicator{
        void openSingleProductDetail(Product product);
    }
}
