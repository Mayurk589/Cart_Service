package com.tcs.tcskart.cart.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ApiData {
	private Long userId;
	private Long productId;
	private Integer quantity;
	
}
