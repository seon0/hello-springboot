package com.example.demo.domain.post.dto;

import java.time.LocalDateTime;

import com.example.demo.domain.post.entity.Post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponse {
	private Long id;
	private String title;
	private String content;
	private Long userId;
	private String username;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	private long likeCount;
	private boolean likedByMe;
	
	public static PostDetailResponse from(Post post, Long likeCount, boolean likedByMe) {
		return PostDetailResponse.builder()
				.id(post.getId())
				.title(post.getTitle())
				.content(post.getContent())
				.userId(post.getUser().getId())
				.username(post.getUser().getUsername())
				.createdAt(post.getCreatedAt())
				.updatedAt(post.getUpdatedAt())
				.likeCount(likeCount)
				.likedByMe(likedByMe)
				.build();
	}

}
