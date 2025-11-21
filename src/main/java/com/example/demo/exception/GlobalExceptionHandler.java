package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dto.ResponseDto;

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
		
		return ResponseDto.fail(e.getMessage());
	}

}
