package com.example.demo.global.exception;

public class UnauthorizedException extends CustomException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6483960338814753253L;
	
	public UnauthorizedException() {
		super(ErrorCode.UNAUTHORIZED);
	}
	
	public UnauthorizedException(String message) {
		super(ErrorCode.UNAUTHORIZED, message);
	}

}
