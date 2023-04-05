package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductCategory implements Serializable {

	@SerializedName("categoryName")
	private String categoryName;

	@SerializedName("categoryId")
	private String categoryId;

	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public String getCategoryName(){
		return categoryName;
	}

	public void setCategoryId(String categoryId){
		this.categoryId = categoryId;
	}

	public String getCategoryId(){
		return categoryId;
	}

	@Override
 	public String toString(){
		return 
			"ProductCategory{" + 
			"categoryName = '" + categoryName + '\'' + 
			",categoryId = '" + categoryId + '\'' + 
			"}";
		}
}