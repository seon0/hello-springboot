package com.example.demo.global.exception;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseDto<?> handleValidationException(MethodArgumentNotValidException e) {
//		String msg = e.getBindingResult().getFieldError().getDefaultMessage();
//		return ResponseDto.fail(msg);
//	}
//	
//	
//	@ExceptionHandler(NotFoundException.class)
//	public ResponseEntity<?> handleNotFound(NotFoundException e) {
//		return ResponseEntity.status(404).body(e.getMessage());
//	}
//
//	@ExceptionHandler(UnauthorizedException.class)
//	public ResponseEntity<?> handleUnauthorized(UnauthorizedException e) {
//		return ResponseEntity.status(403).body(e.getMessage());
//	}
//	
	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> handleCustomException(CustomException e) {
		return ResponseEntity
						.status(e.getErrorCode().getStatus())
						.body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
//		return ResponseDto.fail(e.getMessage());
	}
	
	
	// 이제 모든 에러는 일관된 JSON으로 나옴.
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
		String uri = request.getRequestURI();
		
		if ( uri.startsWith("/v3/api-docs") || 
				uri.startsWith("/swagger-ui") || 
				uri.startsWith("/swagger-resources") || 
				uri.startsWith("/h2-console") ) {
			return null;
		}
		
		e.printStackTrace();
//		return ResponseDto.fail("서버 오류:" + e.getMessage());
		return ResponseEntity
						.status(500)
						.body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage()));
	}


	/**
	 *  ErrorResponse 에서는 int status, String code, String message  최대로 이 3가지 변수를 사용한다.
	 *  
	 *  그런데, 2개의 인자를 받아서 객체를 만들고 싶으면 아래와 같이.
	 *  안에 생성자를 재정의 해서 사용할 수 있다. 
	 *  
	 *  ( record 클래스는 변수의 재정의 XXXXX )
	 */
	record ErrorResponse(int status, String code, String message) {
		ErrorResponse(ErrorCode ec, String message) {
			this(ec.getStatus().value(), ec.name(), message);
		}
	}
	
	private Map<String, Object> error(String code, String message) {
		return Map.of(
				"timestamp", LocalDateTime.now(),
				"code", code,
				"message", message
		);
	}
	
	
}
