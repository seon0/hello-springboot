package com.example.demo.domain.like.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.like.service.LikeService;
import com.example.demo.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class LikeController {
	
	private final LikeService likeService;
	
	private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/like")
	public String toggleLike(
			@PathVariable Long postId,
			@RequestHeader("Authorization") String token) {
		Long userId = jwtTokenProvider.validateAndGetUserId(token.replace("Bearer ", ""));
		
		boolean liked = likeService.toggleLike(postId, userId);
		
		return liked ? "LIKED" : "UNLIKED";
	}
	
	@GetMapping("/like-count")
	public long getLikeCount(@PathVariable Long postId) {
		return likeService.getLikeCount(postId);
	}

}
