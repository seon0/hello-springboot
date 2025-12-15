package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> { // <T> 제네릭타입으로 생성
	private boolean success;
	private T data;
	private String message;
	
	
	// 제네릭 타입의 메소드는 static으로 만들어야하는듯 하다.
	
	public static <T> ResponseDto<T> success(T data) {
		return new ResponseDto<T>(true, data, null);
	}
	
	public static ResponseDto<?> fail(String message) {
		return new ResponseDto<>(false, null, message);
	}

}
