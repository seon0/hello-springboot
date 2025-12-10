package com.example.demo.domain.comment.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {
	private Long id;
	private String content;
	private Long authorId;
	private String authorName;

}
