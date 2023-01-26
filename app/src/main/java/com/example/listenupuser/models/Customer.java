package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class Customer{

	@SerializedName("userAddress")
	private String userAddress;

	@SerializedName("userName")
	private String userName;

	@SerializedName("userId")
	private String userId;

	@SerializedName("email")
	private String email;

	public void setUserAddress(String userAddress){
		this.userAddress = userAddress;
	}

	public String getUserAddress(){
		return userAddress;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	@Override
 	public String toString(){
		return 
			"Customer{" + 
			"userAddress = '" + userAddress + '\'' + 
			",userName = '" + userName + '\'' + 
			",userId = '" + userId + '\'' + 
			",email = '" + email + '\'' + 
			"}";
		}
}