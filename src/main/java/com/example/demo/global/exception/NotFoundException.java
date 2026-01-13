package com.example.demo.global.exception;

public class NotFoundException extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4702843070793496334L;
	
	public NotFoundException() {
		super(ErrorCode.NOT_FOUND);
	}
	
	public NotFoundException(String message) {
		super(ErrorCode.NOT_FOUND, message);
	}

}
