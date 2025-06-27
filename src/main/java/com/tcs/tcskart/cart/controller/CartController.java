package com.tcs.tcskart.cart.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService service;
	
	public CartController(CartService service) {
		this.service = service;
	}

	@GetMapping("/view")
	public Cart getAllCartItemOfUserId(@RequestParam("userId") Long userId) throws Exception{
		return service.getCartByUserId(userId);
	}
	
	@PostMapping("/add")
	public String addProductToUserCart(@RequestParam("productId") Long productId, @RequestParam("userId") Long userId) {
		if(service.addProductToUserCart(productId , userId))
			return "Product added to cart";
		return "Problem in adding this product to cart";
	}
}
