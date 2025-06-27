package com.tcs.tcskart.cart.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tcs.tcskart.cart.exception.NoCartItemException;
import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.model.CartItem;
import com.tcs.tcskart.cart.repository.CartItemRepository;
import com.tcs.tcskart.cart.repository.CartRepository;

@Service
public class CartService implements CartServiceInterface {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	
	
	public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository) {
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
	}

	
	// return Cart Items by Cart Id
	@Override
	public CartItem getCartItemByCartId(Long cartId) {
		Optional<CartItem> resultCartItem =  cartItemRepository.findById(cartId);
		if(resultCartItem.isEmpty())
			return null;
		return resultCartItem.get();
	}

	
	//return all Cart of User by User Id
	@Override
	public Cart getCartByUserId(Long userId) throws NoCartItemException {
		Optional<Cart> cartExist = cartRepository.findByUserId(userId);
		if(cartExist.isEmpty())
			throw new NoCartItemException();
		return cartExist.get();
	}

	//add product to cart item then add to cart 
	@Override
	public Boolean addProductToUserCart(Long productId, Long userId) {
		Optional<CartItem> cartItemExist = cartItemRepository.findByProductIdAndUserId(productId, userId);
		CartItem cartItem;
		
		Optional<Cart> cartExist = cartRepository.findByUserId(userId);
		Cart cart;
		
		//get price from product service by productId
		Double price = 2000.0;
		
		if(cartExist.isPresent())
			cart = cartExist.get();
		else {
			cart = new Cart(userId, 0.0);
			cart = cartRepository.save(cart);
		}
		
		//if product is already in cart
		if(cartItemExist.isPresent()) {
			cartItem = cartItemExist.get();
			cartItem.setPrice(cartItem.getPrice() + price);
			cartItem.setQuantity(cartItem.getQuantity() + 1);
		}
		
		//if product is not in cart then creating cart item
		else {
			cartItem = new CartItem(cart.getCartId(), userId, productId, 1, price);
		}
		
		//updating or adding cart item in database
		cart.setTotalPrice(cart.getTotalPrice() + cartItem.getPrice());
		cart.getCartItems().add(cartItem);
		if(cartItemRepository.save(cartItem)==null)
			return false;
		if(cartRepository.save(cart)==null)
			return false;
		return true;
	}

	@Override
	public Boolean deleteCartItemByItemId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAllCartItemOfUserId(Long userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
