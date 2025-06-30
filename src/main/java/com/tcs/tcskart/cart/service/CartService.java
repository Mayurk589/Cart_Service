package com.tcs.tcskart.cart.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tcs.tcskart.cart.dto.Product;
import com.tcs.tcskart.cart.dto.ProductImages;
import com.tcs.tcskart.cart.exception.NoCartItemException;
import com.tcs.tcskart.cart.exception.ProductNotPresentInCartException;
import com.tcs.tcskart.cart.model.Cart;
import com.tcs.tcskart.cart.model.CartItem;
import com.tcs.tcskart.cart.repository.CartItemRepository;
import com.tcs.tcskart.cart.repository.CartRepository;

@Service
public class CartService implements CartServiceInterface {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	
	//constructor
	public CartService(CartItemRepository cartItemRepository, CartRepository cartRepository) {
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
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
	public Boolean addProductToUserCart(Long userId, Long productId, Integer quantity) {

		//checking whether cart for this user had been created or not
		Optional<Cart> cartExist = cartRepository.findByUserId(userId);
		Cart cart;
		
		if(cartExist.isPresent())
			cart = cartExist.get(); //if exists, fetching the same cart
		else {
			cart = new Cart(userId, 0.0);
			cart = cartRepository.save(cart); // if not exists, creating new cart
		}
		
		//get product info from product service by productId
//		RestTemplate restTemplate = new RestTemplate();
//		String productServiceUrl = "http://localhost:8080/api/products/getProduct/" + productId;
//		ResponseEntity<Product> product = restTemplate.exchange(productServiceUrl, HttpMethod.GET, null, Product.class);, 
		List<ProductImages> tempImages = new ArrayList<>();
		tempImages.add(new ProductImages(Long.valueOf(1), "url-1"));
		tempImages.add(new ProductImages(Long.valueOf(2), "url-2"));
		Product product = new Product(Long.valueOf(10), "Guerilla 450", "A best bike in 450cc segment", 1000.0, "Bike", 50, null, tempImages);
		
		
		Optional<CartItem> cartItemExist = cartItemRepository.findByProductIdAndUserId(productId, userId);
		CartItem cartItem;
		
		//if product is already in cart
		if(cartItemExist.isPresent()) {
			cartItem = cartItemExist.get();
			cartItem.setProductName(product.getName());
			cartItem.setImgUrl(product.getImgUrl().get(0).getUrl());
			cartItem.setPrice(cartItem.getPrice() + (quantity*product.getPrice() ));
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
			cartItem.setAddedAt(LocalDateTime.now());
		}
		
		//if product is not in cart then creating cart item
		else {
			cartItem = new CartItem(cart.getCartId(), userId, productId, product.getName(), product.getImgUrl().get(0).getUrl(), quantity, product.getPrice()*quantity);
		}
		
		//updating or adding cart item in database
		cart.getCartItems().add(cartItem);
		cart.updateTotalPrice();
		if(cartItemRepository.save(cartItem)==null)
			return false;
		if(cartRepository.save(cart)==null)
			return false;
		return true;
	}

	//delete product from cart by 1
	@Override
	public Boolean deleteCartItemByProductId(Long userId, Long productId, Integer quantity) throws Exception {
		
		//checking whether cart of this user exists or not
		Optional<Cart> cartExist = cartRepository.findByUserId(userId);
		if(cartExist.isEmpty())
			throw new NoCartItemException();
		Cart cart = cartExist.get();
		
		//checking whether this product is present in this user cart or not
		Optional<CartItem> cartItemExist = cartItemRepository.findByProductIdAndUserId(productId, userId);
		if(cartItemExist.isEmpty())
			throw new ProductNotPresentInCartException();
		CartItem cartItem = cartItemExist.get();
		
		//product quantity is same as quantity to remove
		if(cartItem.getQuantity()==quantity) {
			cartItemRepository.deleteById(cartItem.getCartId()); //remove from CartItem table
			cart.getCartItems().remove(cartItem);
			cart.updateTotalPrice();
			
		}
		
		//product quantity is more than quantity to remove
		else {
			cart.getCartItems().remove(cartItem);
			cartItem.setPrice(cartItem.getPrice() - (cartItem.getPrice()/cartItem.getQuantity()) * quantity);
			cartItem.setQuantity(cartItem.getQuantity() - quantity);
			if(cartItemRepository.save(cartItem)==null)
				return false;
			cart.getCartItems().add(cartItem);
		}
		
		//if cart is empty then delete the cart
		if(!cartItemRepository.existsByUserId(userId)) {
			cartRepository.deleteByUserId(userId);
			return true;
		}
			
		//updating cart table
		if(cartRepository.save(cart)==null) 
			return false;
		
		return true;
	}

	//clear all items from cart and also cart by userId 
	@Override
	public Boolean deleteAllCartItemOfUserId(Long userId) throws Exception {
		//checking cart is present or not
		if(!cartRepository.existsByUserId(userId))
			throw new NoCartItemException();
		cartItemRepository.deleteAllByUserId(userId);
		cartRepository.deleteByUserId(userId);
		return true;
	}
	
}
