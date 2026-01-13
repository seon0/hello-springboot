package com.example.demo.domain.like.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.like.service.LikeService;
import com.example.demo.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts/{postId}")
public class LikeController {
	
	private final LikeService likeService;
	
	
	@PostMapping("/like")
	public String toggleLike(
			@PathVariable Long postId,
			Authentication auth) {
		Long userId = ( (User) auth.getPrincipal() ).getId();
		
		boolean liked = likeService.toggleLike(postId, userId);
		
		return liked ? "LIKED" : "UNLIKED";
	}
	
	@GetMapping("/like-count")
	public long getLikeCount(@PathVariable Long postId) {
		return likeService.getLikeCount(postId);
	}

}
