package com.example.demo.domain.user.exception;

import com.example.demo.global.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5261300214983306383L;
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
	
}
