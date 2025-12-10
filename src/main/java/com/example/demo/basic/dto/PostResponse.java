package com.example.demo.basic.dto;

import com.example.demo.basic.entity.Posts;

import lombok.Data;

@Data
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	
	public PostResponse(Posts post) {
		super();
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
	
	
}
