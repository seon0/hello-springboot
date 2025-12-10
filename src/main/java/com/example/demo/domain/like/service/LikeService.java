package com.example.demo.domain.like.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.like.entity.Like;
import com.example.demo.domain.like.repository.LikeRepository;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.error.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {
	
	private final LikeRepository likeRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	public boolean toggleLike(Long postId, Long userId) {
		Post post = postRepository.findById(postId)
				.orElseThrow( () -> new NotFoundException("게시글을 찾을 수 없습니다.")) ;

		User user = userRepository.findById(userId)
				.orElseThrow( () -> new NotFoundException("유저를 찾을 수 없습니다."));
		
		return likeRepository.findByUserAndPost(user, post)
				.map( existingLike -> {
					// 눌렀으면, 취소
					likeRepository.delete(existingLike);
					return false; // 좋아요 취소됨
				})
				.orElseGet( () -> {
					Like newLike = Like.builder()
							.user(user)
							.post(post)
							.build();
					likeRepository.save(newLike);
					return true;
				});
		
	}
	
	public long getLikeCount(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow( () -> new NotFoundException("게시글을 찾을 수 없습니다.")) ;
		
		return likeRepository.countByPost(post);
	}
	
	

}
