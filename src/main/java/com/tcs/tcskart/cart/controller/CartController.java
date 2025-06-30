package com.tcs.tcskart.cart.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.tcskart.cart.dto.ApiData;
import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService service;
	
	public CartController(CartService service) {
		this.service = service;
	}

	@GetMapping("/view/{userId}")
	public ResponseEntity<Cart> getAllCartItemOfUserId(@PathVariable Long userId) throws Exception{
		Cart cart = service.getCartByUserId(userId);
		if(cart != null)
			return ResponseEntity.ok().body(cart);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	
	@PostMapping("/add")
	public ResponseEntity<String> addProductToUserCart(@RequestBody ApiData data) {
		if(service.addProductToUserCart(data.getUserId(), data.getProductId(), data.getQuantity()))
			return ResponseEntity.ok().body("Product added to cart");
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Problem in adding this product to cart");
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<String> removeProductFromUser(@RequestBody ApiData data) throws Exception{
		if(service.deleteCartItemByProductId(data.getUserId(), data.getProductId(), data.getQuantity()))
			return ResponseEntity.ok().body("Product removed successfully");
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Product removing failed");
	}
	
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<String> clearCartOfUser(@PathVariable Long userId) throws Exception{
		if(service.deleteAllCartItemOfUserId(userId))
			return ResponseEntity.ok().body("Cart has been cleared");
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Clearing cart is failed");
	}
}
