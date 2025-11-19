package com.example.demo.dto.response;

import com.example.demo.entity.Post;

import lombok.Data;

@Data
public class PostResponse {
	private Long id;
	private String title;
	private String content;
	
	public PostResponse(Post post) {
		super();
		this.id = post.getId();
		this.title = post.getTitle();
		this.content = post.getContent();
	}
	
	
}
