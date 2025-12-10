package com.example.demo.basic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponseDto {
	
	private Long id;
	private String title;
	private String content;

}
