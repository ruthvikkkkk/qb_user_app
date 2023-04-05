package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class CartItem implements Serializable {

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("total_quantity")
	private int totalQuantity;
	@SerializedName("sku_ID")
	private String skuID;

	@SerializedName("productDetails")
	private ProductDetails productDetails;

	@SerializedName("productPrice")
	private int productPrice;

	@SerializedName("image")
	private String image;

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public void setImage(String image){this.image = image;}
	public String getImage(){return this.image;}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setSkuID(String skuID){
		this.skuID = skuID;
	}

	public String getSkuID(){
		return skuID;
	}

	public void setProductDetails(ProductDetails productDetails){
		this.productDetails = productDetails;
	}

	public ProductDetails getProductDetails(){
		return productDetails;
	}

	public void setProductPrice(int productPrice){
		this.productPrice = productPrice;
	}

	public int getProductPrice(){
		return productPrice;
	}

	@Override
 	public String toString(){
		return 
			"CartItem{" +
			"quantity = '" + quantity + '\'' + 
			",sku_ID = '" + skuID + '\'' + 
			",productDetails = '" + productDetails + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			"}";
		}
}