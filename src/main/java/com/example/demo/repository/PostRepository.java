package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
	// JpaRepository 설정으로,  세부 내용 구현 안해도 됨.
	// JPA가 만들어줌~

}
