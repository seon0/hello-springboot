package com.example.demo.basic.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Posts {
	
	/*
	 * @Entity
	 * 이 클래스가 DB 테이블
	 * 
	 * @Id, @GeneratedValue
	 * 자동 증가 PK
	 * 
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String content;

}
