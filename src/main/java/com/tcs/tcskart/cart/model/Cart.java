package com.tcs.tcskart.cart.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="carts")
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;
	
	@Column(unique = true)
	private Long userId;
	
	@OneToMany
	@JoinColumn(name="cart_Id")
	private List<CartItem> cartItems ;
	
	private Double totalPrice;

	public Cart(Long userId, Double totalPrice) {
		this.userId = userId;
		this.cartItems =  new ArrayList<CartItem>();
		this.totalPrice = totalPrice;
	}
	
	public void updateTotalPrice() {
		Double priceSum = 0.0;
		for(CartItem cartItem : cartItems) {
			priceSum += cartItem.getPrice();
		}
		this.totalPrice = priceSum;
	}
	
	
}
