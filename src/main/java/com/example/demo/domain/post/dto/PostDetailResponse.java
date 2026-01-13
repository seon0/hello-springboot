package com.example.demo.domain.post.dto;

import java.time.LocalDateTime;

import com.example.demo.domain.post.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
		System.out.println(post.getUser().toString());
		System.out.println(post.getUser().getEmail());
		System.out.println(post.getUser().getUsername());
		System.out.println(post.getUser().getId());
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
