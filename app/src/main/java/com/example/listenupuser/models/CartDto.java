package com.example.listenupuser.models;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CartDto implements Serializable {

	@SerializedName("dateTime")
	private String dateTime;

	@SerializedName("totalOrderCost")
	private int totalOrderCost;

	@SerializedName("updatedBy")
	private String updatedBy;

	@SerializedName("createdBy")
	private String createdBy;

	@SerializedName("cartId")
	private String cartId;

	@SerializedName("guest")
	private boolean guest;

	@SerializedName("cartItems")
	private List<CartItem> cartItems;

	@SerializedName("customer")
	private Customer customer;

	public void setDateTime(String dateTime){
		this.dateTime = dateTime;
	}

	public String getDateTime(){
		return dateTime;
	}

	public void setTotalOrderCost(int totalOrderCost){
		this.totalOrderCost = totalOrderCost;
	}

	public int getTotalOrderCost(){
		return totalOrderCost;
	}

	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;
	}

	public String getUpdatedBy(){
		return updatedBy;
	}

	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;
	}

	public String getCreatedBy(){
		return createdBy;
	}

	public void setCartId(String cartId){
		this.cartId = cartId;
	}

	public String getCartId(){
		return cartId;
	}

	public void setGuest(boolean guest){
		this.guest = guest;
	}

	public boolean isGuest(){
		return guest;
	}

	public void setCartItems(List<CartItem> cartItems){
		this.cartItems = cartItems;
	}

	public List<CartItem> getCartItems(){
		return cartItems;
	}

	public void setCustomer(Customer customer){
		this.customer = customer;
	}

	public Customer getCustomer(){
		return customer;
	}

	@Override
 	public String toString(){
		return 
			"CartDto{" + 
			"dateTime = '" + dateTime + '\'' + 
			",totalOrderCost = '" + totalOrderCost + '\'' + 
			",updatedBy = '" + updatedBy + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",cartId = '" + cartId + '\'' + 
			",guest = '" + guest + '\'' + 
			",cartItems = '" + cartItems + '\'' + 
			",customer = '" + customer + '\'' + 
			"}";
		}
}