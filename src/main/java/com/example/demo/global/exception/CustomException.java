package com.example.demo.global.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6639946694413903876L;
	
	private final ErrorCode errorCode;
	
	
	public CustomException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}

	// 임의로 추가함.
	public CustomException(ErrorCode errorCode, String message) {
		super(message);
		this.errorCode = errorCode;
	}

}
