package com.example.demo.domain.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.comment.entity.Comment;
import com.example.demo.domain.post.entity.Post;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	List<Comment> findByPostId(Long postId);
	
	List<Comment> findByPost(Post post);

}
