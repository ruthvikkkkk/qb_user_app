package com.example.listenupuser.models;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("password")
	private String password;

	@SerializedName("address")
	private String address;

	@SerializedName("city")
	private String city;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("state")
	private String state;

	@SerializedName("email")
	private String email;

	@SerializedName("username")
	private String username;

	public User(String email, String password, String username, String mobile, String address, String city, String state){
		this.email = email;
		this.address = address;
		this.city = city;
		this.state = state;
		this.mobile = mobile;
		this.password = password;
		this.username = username;
	}

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

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return city;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"User{" + 
			"password = '" + password + '\'' + 
			",address = '" + address + '\'' + 
			",city = '" + city + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",state = '" + state + '\'' + 
			",email = '" + email + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}