package com.example.listenupuser.models;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Order{

	@SerializedName("dateTime")
	private String dateTime;

	@SerializedName("totalOrderCost")
	private int totalOrderCost;

	@SerializedName("updatedBy")
	private String updatedBy;

	@SerializedName("createdBy")
	private String createdBy;

	@SerializedName("orderId")
	private String orderId;

	@SerializedName("cartId")
	private String cartId;

	@SerializedName("cartItems")
	private List<CartItemsItem> cartItems;

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

	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	public String getOrderId(){
		return orderId;
	}

	public void setCartId(String cartId){
		this.cartId = cartId;
	}

	public String getCartId(){
		return cartId;
	}

	public void setCartItems(List<CartItemsItem> cartItems){
		this.cartItems = cartItems;
	}

	public List<CartItemsItem> getCartItems(){
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
			"Order{" + 
			"dateTime = '" + dateTime + '\'' + 
			",totalOrderCost = '" + totalOrderCost + '\'' + 
			",updatedBy = '" + updatedBy + '\'' + 
			",createdBy = '" + createdBy + '\'' + 
			",orderId = '" + orderId + '\'' + 
			",cartId = '" + cartId + '\'' + 
			",cartItems = '" + cartItems + '\'' + 
			",customer = '" + customer + '\'' + 
			"}";
		}
}