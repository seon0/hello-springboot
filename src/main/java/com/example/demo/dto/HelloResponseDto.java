package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor	
public class HelloResponseDto {
	/* 
	 * @Data
	 * getter, setter, tostring 자동 생성
	 * 
	 * @AllArgsConstrictor
	 * 모든 필드를 받는 생성자 자동 생성
	 */
	
	private String message;
	private String name;
	private int count;

}
