package com.example.demo.domain.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.post.dto.PostRequestDto;
import com.example.demo.domain.post.dto.PostResponse;
import com.example.demo.domain.post.dto.PostSearchCondition;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.user.entity.User;
import com.example.demo.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;
	
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto, Authentication auth) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return ResponseEntity.ok(postService.createPost(userId, dto));
	}
	
	@GetMapping
	public ResponseDto<?> getPosts() {
		return postService.getPosts();
	}
	
	@GetMapping("/{id}")
	public ResponseDto<?> getPost(@PathVariable Long id, Authentication auth) {
		Long userId = auth != null 
				? ( (User)auth.getPrincipal() ).getId() 
				: null;
		return postService.getPost(id, userId);
	}
	
	@PutMapping("/{id}")
	public ResponseDto<?> updatePost(
			@PathVariable Long id,
			@RequestBody PostUpdateRequest dto,
			Authentication auth
			) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return postService.updatePost(userId, id, dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseDto<?> deletePost(
			@PathVariable Long id,
			Authentication auth
			) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return postService.deletePost(userId, id);
	}

	@GetMapping("/search")
	public ResponseDto<Page<PostResponse>> searchFinal(
			PostSearchCondition cond,
			Pageable pageable,
			Authentication auth
	) {
		Long userId = auth != null 
				? ( (User)auth.getPrincipal() ).getId() 
				: null;
		return ResponseDto.success(postService.searchFinal(cond, pageable, userId));
	}
	
}
