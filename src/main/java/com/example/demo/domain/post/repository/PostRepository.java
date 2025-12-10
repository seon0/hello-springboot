package com.example.demo.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{

}
