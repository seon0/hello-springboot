package com.example.demo.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6639946694413903876L;
	
	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		super(message);
	}
	

}
