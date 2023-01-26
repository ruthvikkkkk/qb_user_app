package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class CartItemsItem{

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("sku_ID")
	private String skuID;

	@SerializedName("productDetails")
	private ProductDetails productDetails;

	@SerializedName("productPrice")
	private int productPrice;

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
			"CartItemsItem{" + 
			"quantity = '" + quantity + '\'' + 
			",sku_ID = '" + skuID + '\'' + 
			",productDetails = '" + productDetails + '\'' + 
			",productPrice = '" + productPrice + '\'' + 
			"}";
		}
}