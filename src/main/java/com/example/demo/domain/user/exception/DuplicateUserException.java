package com.example.demo.domain.user.exception;

import com.example.demo.global.error.CustomException;
import com.example.demo.global.error.ErrorCode;

public class DuplicateUserException extends CustomException{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8308873163065782431L;
	
	
	public DuplicateUserException() {
		super(ErrorCode.BAD_REQUEST);
	}
	
	public DuplicateUserException(String msg) {
		super(ErrorCode.BAD_REQUEST, msg);
	}

}
