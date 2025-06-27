package com.tcs.tcskart.cart.service;


import com.tcs.tcskart.cart.exception.NoCartItemException;
import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.model.CartItem;

public interface CartServiceInterface {
	
	public CartItem getCartItemByCartId(Long cartId);
	
	public Cart getCartByUserId(Long userId) throws NoCartItemException;
	
	public Boolean addProductToUserCart(Long productId, Long userId);
	
	public Boolean deleteCartItemByItemId(Long cardId);
	
	public Boolean deleteAllCartItemOfUserId(Long userId);
	
}
