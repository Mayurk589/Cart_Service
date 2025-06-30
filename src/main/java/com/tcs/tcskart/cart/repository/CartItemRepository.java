package com.tcs.tcskart.cart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.tcskart.cart.model.CartItem;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	public List<CartItem> findAllByUserId(Long userId);

	public Optional<CartItem> findByProductIdAndUserId(Long productId, Long userId);

	public void deleteAllByUserId(Long userId);

	public boolean existsByUserId(Long userId);

}
