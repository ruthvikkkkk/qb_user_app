package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

	@SerializedName("updatedBy")
	private String updatedBy;

	@SerializedName("productID")
	private String productID;

	@SerializedName("highBass")
	private String highBass;

	@SerializedName("productBrand")
	private String productBrand;

	@SerializedName("updatedDate")
	private String updatedDate;

	@SerializedName("productName")
	private String productName;

	@SerializedName("productCategory")
	private ProductCategory productCategory;

	@SerializedName("createdDate")
	private String createdDate;

	@SerializedName("waterResistant")
	private String waterResistant;

	@SerializedName("createdBy")
	private String createdBy;

	@SerializedName("imageURL")
	private String imageURL;

	@SerializedName("aptX")
	private String aptX;

	@SerializedName("productDescription")
	private String productDescription;

	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public void setProductID(String productID){
		this.productID = productID;
	}

	public String getProductID(){
		return productID;
	}

	public void setHighBass(String highBass){
		this.highBass = highBass;
	}

	public String getHighBass(){
		return highBass;
	}

	public void setProductBrand(String productBrand){
		this.productBrand = productBrand;
	}

	public String getProductBrand(){
		return productBrand;
	}

	public void setUpdatedDate(String updatedDate){
		this.updatedDate = updatedDate;
	}

	public String getUpdatedDate(){
		return updatedDate;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductCategory(ProductCategory productCategory){
		this.productCategory = productCategory;
	}

	public ProductCategory getProductCategory(){
		return productCategory;
	}

	public void setCreatedDate(String createdDate){
		this.createdDate = createdDate;
	}

	public String getCreatedDate(){
		return createdDate;
	}

	public void setWaterResistant(String waterResistant){
		this.waterResistant = waterResistant;
	}

	public String getWaterResistant(){
		return waterResistant;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

	public String getImageURL(){
		return imageURL;
	}

	public void setAptX(String aptX){
		this.aptX = aptX;
	}

	public String getAptX(){
		return aptX;
	}

	public void setProductDescription(String productDescription){
		this.productDescription = productDescription;
	}

	public String getProductDescription(){
		return productDescription;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"updatedBy = '" + updatedBy + '\'' + 
			",productID = '" + productID + '\'' + 
			",highBass = '" + highBass + '\'' + 
			",productBrand = '" + productBrand + '\'' + 
			",updatedDate = '" + updatedDate + '\'' + 
			",productName = '" + productName + '\'' + 
			",productCategory = '" + productCategory + '\'' + 
			",createdDate = '" + createdDate + '\'' + 
			",waterResistant = '" + waterResistant + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",imageURL = '" + imageURL + '\'' + 
			",aptX = '" + aptX + '\'' + 
			",productDescription = '" + productDescription + '\'' + 
			"}";
		}
}