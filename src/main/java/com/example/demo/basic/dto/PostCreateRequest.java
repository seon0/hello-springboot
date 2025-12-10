package com.example.demo.basic.dto;

import lombok.Data;

@Data
public class PostCreateRequest {
	private String title;
	private String content;
}
