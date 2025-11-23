package com.example.demo.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ResponseDto;
import com.example.demo.global.exception.CustomException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler { // 전역적으로 예외 발생 시 handle
	
	// 이제 모든 에러는 일관된 JSON으로 나옴.
	@ExceptionHandler(Exception.class)
	public ResponseDto<?> handleException(Exception e, HttpServletRequest request) {
		String uri = request.getRequestURI();
		
		if ( uri.startsWith("/v3/api-docs") || 
				uri.startsWith("/swagger-ui") || 
				uri.startsWith("/swagger-resources") ) {
			return null;
		}
		
		return ResponseDto.fail("서버 오류:" + e.getMessage());
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseDto<?> handleValidationException(MethodArgumentNotValidException e) {
		String msg = e.getBindingResult().getFieldError().getDefaultMessage();
		return ResponseDto.fail(msg);
	}
	
	@ExceptionHandler(CustomException.class)
	public ResponseDto<?> handleCustomException(CustomException e) {
		return ResponseDto.fail(e.getMessage());
	}
	
}
