package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class Stock{

	@SerializedName("image")
	private String image;

	@SerializedName("quantity")
	private int quantity;

	@SerializedName("modifyDate")
	private String modifyDate;

	@SerializedName("productId")
	private String productId;

	@SerializedName("price")
	private int price;

	@SerializedName("merchant")
	private Merchant merchant;

	@SerializedName("productName")
	private String productName;

	@SerializedName("skuId")
	private String skuId;

	@SerializedName("createDate")
	private String createDate;

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setModifyDate(String modifyDate){
		this.modifyDate = modifyDate;
	}

	public String getModifyDate(){
		return modifyDate;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public void setMerchant(Merchant merchant){
		this.merchant = merchant;
	}

	public Merchant getMerchant(){
		return merchant;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setSkuId(String skuId){
		this.skuId = skuId;
	}

	public String getSkuId(){
		return skuId;
	}

	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}

	public String getCreateDate(){
		return createDate;
	}

	@Override
 	public String toString(){
		return 
			"Stock{" + 
			"image = '" + image + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",modifyDate = '" + modifyDate + '\'' + 
			",productId = '" + productId + '\'' + 
			",price = '" + price + '\'' + 
			",merchant = '" + merchant + '\'' + 
			",productName = '" + productName + '\'' + 
			",skuId = '" + skuId + '\'' + 
			",createDate = '" + createDate + '\'' + 
			"}";
		}
}