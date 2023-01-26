package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable {

	@SerializedName("createdDate")
	private Object createdDate;

	@SerializedName("updatedBy")
	private Object updatedBy;

	@SerializedName("productID")
	private String productID;

	@SerializedName("createdBy")
	private Object createdBy;

	@SerializedName("deleteStatus")
	private boolean deleteStatus;

	@SerializedName("productBrand")
	private String productBrand;

	@SerializedName("imageURL")
	private String imageURL;

	@SerializedName("updatedDate")
	private Object updatedDate;

	@SerializedName("productName")
	private String productName;

	@SerializedName("productDescription")
	private String productDescription;

	@SerializedName("productUSP")
	private Object productUSP;

	@SerializedName("productCategory")
	private ProductCategory productCategory;

	public void setCreatedDate(Object createdDate){
		this.createdDate = createdDate;
	}

	public Object getCreatedDate(){
		return createdDate;
	}

	public void setUpdatedBy(Object updatedBy){
		this.updatedBy = updatedBy;
	}

	public Object getUpdatedBy(){
		return updatedBy;
	}

	public void setProductID(String productID){
		this.productID = productID;
	}

	public String getProductID(){
		return productID;
	}

	public void setCreatedBy(Object createdBy){
		this.createdBy = createdBy;
	}

	public Object getCreatedBy(){
		return createdBy;
	}

	public void setDeleteStatus(boolean deleteStatus){
		this.deleteStatus = deleteStatus;
	}

	public boolean isDeleteStatus(){
		return deleteStatus;
	}

	public void setProductBrand(String productBrand){
		this.productBrand = productBrand;
	}

	public String getProductBrand(){
		return productBrand;
	}

	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}

	public String getImageURL(){
		return imageURL;
	}

	public void setUpdatedDate(Object updatedDate){
		this.updatedDate = updatedDate;
	}

	public Object getUpdatedDate(){
		return updatedDate;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductDescription(String productDescription){
		this.productDescription = productDescription;
	}

	public String getProductDescription(){
		return productDescription;
	}

	public void setProductUSP(Object productUSP){
		this.productUSP = productUSP;
	}

	public Object getProductUSP(){
		return productUSP;
	}

	public void setProductCategory(ProductCategory productCategory){
		this.productCategory = productCategory;
	}

	public ProductCategory getProductCategory(){
		return productCategory;
	}

	@Override
 	public String toString(){
		return 
			"Product{" + 
			"createdDate = '" + createdDate + '\'' + 
			",updatedBy = '" + updatedBy + '\'' + 
			",productID = '" + productID + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",deleteStatus = '" + deleteStatus + '\'' + 
			",productBrand = '" + productBrand + '\'' + 
			",imageURL = '" + imageURL + '\'' + 
			",updatedDate = '" + updatedDate + '\'' + 
			",productName = '" + productName + '\'' + 
			",productDescription = '" + productDescription + '\'' + 
			",productUSP = '" + productUSP + '\'' + 
			",productCategory = '" + productCategory + '\'' + 
			"}";
		}
}