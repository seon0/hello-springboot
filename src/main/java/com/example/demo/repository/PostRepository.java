package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	// JpaRepository 설정으로,  JPA가 만들어줘서, 기본적인 내용 구현 안해도 됨.
	
	//JpaRepository 안에 이미 findAll(Pageable pageable)이 기본 제공된다!
	// 그래서 아무것도 안 넣어도 됨.
	Page<Post> findAll(Pageable pageable);
	
	// title LIKE %keyword%
	List<Post> findByTitleContaining(String keyword);

}
