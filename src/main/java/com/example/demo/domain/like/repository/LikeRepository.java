package com.example.demo.domain.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.like.entity.Like;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.user.entity.User;

public interface LikeRepository extends JpaRepository<Like, Long>{
	
	Optional<Like> findByUserAndPost(User user, Post post);
	
	long countByPost(Post post);

	boolean existsByUserAndPost(User user, Post post);

}
