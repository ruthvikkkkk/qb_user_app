package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class Merchant{

	@SerializedName("password")
	private String password;

	@SerializedName("address")
	private String address;

	@SerializedName("phoneNumber")
	private String phoneNumber;

	@SerializedName("modifyDate")
	private String modifyDate;

	@SerializedName("merchantId")
	private String merchantId;

	@SerializedName("deleteStatus")
	private boolean deleteStatus;

	@SerializedName("name")
	private String name;

	@SerializedName("rating")
	private int rating;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("createDate")
	private String createDate;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public void setModifyDate(String modifyDate){
		this.modifyDate = modifyDate;
	}

	public String getModifyDate(){
		return modifyDate;
	}

	public void setMerchantId(String merchantId){
		this.merchantId = merchantId;
	}

	public String getMerchantId(){
		return merchantId;
	}

	public void setDeleteStatus(boolean deleteStatus){
		this.deleteStatus = deleteStatus;
	}

	public boolean isDeleteStatus(){
		return deleteStatus;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setRating(int rating){
		this.rating = rating;
	}

	public int getRating(){
		return rating;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
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
			"Merchant{" + 
			"password = '" + password + '\'' + 
			",address = '" + address + '\'' + 
			",phoneNumber = '" + phoneNumber + '\'' + 
			",modifyDate = '" + modifyDate + '\'' + 
			",merchantId = '" + merchantId + '\'' + 
			",deleteStatus = '" + deleteStatus + '\'' + 
			",name = '" + name + '\'' + 
			",rating = '" + rating + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",createDate = '" + createDate + '\'' + 
			"}";
		}
}