package com.example.demo.domain.post.dto;

import com.example.demo.domain.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PostResponse {
	
	private Long id;
	private String title;
	private String content;
	private Long writerId;
	private String writerUsername;
	
	public static PostResponse from(Post post) {
		return new PostResponse(
				post.getId(),
				post.getTitle(),
				post.getContent(),
				post.getWriter().getId(),
				post.getWriter().getUsername()
		);
	}

}
