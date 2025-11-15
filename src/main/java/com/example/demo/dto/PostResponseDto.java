package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostResponseDto {
	
	private Long id;
	private String title;
	private String content;

}
