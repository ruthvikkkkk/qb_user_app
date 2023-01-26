package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class ProductDetails{

	@SerializedName("productId")
	private String productId;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("productName")
	private String productName;

	@SerializedName("merchantName")
	private String merchantName;

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setMerchantName(String merchantName){
		this.merchantName = merchantName;
	}

	public String getMerchantName(){
		return merchantName;
	}

	@Override
 	public String toString(){
		return 
			"ProductDetails{" + 
			"productId = '" + productId + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",productName = '" + productName + '\'' + 
			",merchantName = '" + merchantName + '\'' + 
			"}";
		}
}