package com.tcs.tcskart.cart.service;


import com.tcs.tcskart.cart.exception.NoCartItemException;
import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.model.CartItem;

public interface CartServiceInterface {
	
	public Cart getCartByUserId(Long userId) throws NoCartItemException;
	
	public Boolean addProductToUserCart(Long userId, Long productId, Integer quantity);
	
	public Boolean deleteCartItemByProductId(Long userId, Long productId, Integer quantity) throws Exception;
	
	public Boolean deleteAllCartItemOfUserId(Long userId) throws Exception;
	
}
