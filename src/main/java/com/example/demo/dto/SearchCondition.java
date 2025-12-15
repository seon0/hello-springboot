package com.example.demo.dto;

import lombok.Data;

@Data
public class SearchCondition {

	private String title;
	private String content;
	private String nickname;
	private String keyword;
	
	private Boolean onlyMine;	// 내가 쓴 글만
	private Boolean onlyActive;	// 삭제되지 않은 글만
	
	private SearchOrder order;	// 정렬 옵션 (ASC/DESC)
}
