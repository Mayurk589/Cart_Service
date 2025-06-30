package com.tcs.tcskart.cart.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class CartItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartItemId;
	
	@Column(name="cart_Id")
	private Long cartId;
	
	private Long userId;
	
	private Long productId;
	
	private String productName;
	
	private String imgUrl;
	
	private Integer quantity;
	
	private Double price;
	
	private LocalDateTime addedAt;

	public CartItem(Long cartId, Long userId, Long productId,String productName, String imgUrl, Integer quantity, Double price) {
		this.cartId = cartId;
		this.userId = userId;
		this.productId = productId;
		this.productName = productName;
		this.imgUrl = imgUrl;
		this.quantity = quantity;
		this.price = price;
		this.addedAt = LocalDateTime.now();
	}
}
