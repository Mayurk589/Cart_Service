package com.tcs.tcskart.cart.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
	
	private Long id;
	private String name;
	private String description;
	private Double price;
	private String category;
	private Integer stockQuantity;
	private LocalDateTime createdAt;
	private List<ProductImages> imgUrl = new ArrayList<>();
}
