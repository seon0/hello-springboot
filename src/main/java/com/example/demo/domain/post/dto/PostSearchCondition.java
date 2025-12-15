package com.example.demo.domain.post.dto;

import java.time.LocalDate;

import com.example.demo.dto.SearchOrder;

import lombok.Data;

@Data
public class PostSearchCondition {

	private String title;
	private String content;
	private String nickname;
	private String keyword;
	private LocalDate fromDate;
	private LocalDate toDate;

	
	private Boolean onlyMine;	// 내가 쓴 글만
	private Boolean onlyActive;	// 삭제되지 않은 글만
	
	private SearchOrder order;	// 정렬 옵션 (ASC/DESC)
	
	private Boolean onlyLiked;	// ★ 내가 좋아요한 글만
	private String sort;				// latest |  views | likes
	
	public PostSearchCondition(String keyword, String content, String nickname, LocalDate from, LocalDate to) {
		this.keyword = keyword;
		this.content = content;
		this.nickname = nickname;
		this.fromDate = from;
		this.toDate = to;
	}
}
