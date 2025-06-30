package com.tcs.tcskart.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.tcskart.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

	public Optional<Cart> findByUserId(Long userId);

	public void deleteByUserId(Long userId);

	public boolean existsByUserId(Long userId);

}
