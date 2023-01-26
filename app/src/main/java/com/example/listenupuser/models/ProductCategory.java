package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductCategory implements Serializable {

	@SerializedName("deleteStatus")
	private boolean deleteStatus;

	@SerializedName("categoryName")
	private String categoryName;

	@SerializedName("categoryId")
	private String categoryId;

	public void setDeleteStatus(boolean deleteStatus){
		this.deleteStatus = deleteStatus;
	}

	public boolean isDeleteStatus(){
		return deleteStatus;
	}

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
			"deleteStatus = '" + deleteStatus + '\'' + 
			",categoryName = '" + categoryName + '\'' + 
			",categoryId = '" + categoryId + '\'' + 
			"}";
		}
}