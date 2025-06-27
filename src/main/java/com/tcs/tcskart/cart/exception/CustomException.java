package com.tcs.tcskart.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomException {
	
	@ExceptionHandler(value = NoCartItemException.class)
	public ResponseEntity<Object> exception(NoCartItemException exception){
		return new ResponseEntity<>("There is no product in cart.",HttpStatus.NOT_FOUND);
	}
	
}
