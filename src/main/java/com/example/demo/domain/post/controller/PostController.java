package com.example.demo.domain.post.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.basic.dto.ResponseDto;
import com.example.demo.domain.post.dto.PostRequestDto;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.global.jwt.JwtTokenProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;
	private final JwtTokenProvider jwtTokenProvider;
	
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto, HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
//		return postService.createPost(token, dto);
		Post post =  postService.createPost(token, dto);
		return ResponseEntity.ok(post);
	}
	
	@GetMapping
	public ResponseDto<?> getPosts() {
		return postService.getPosts();
	}
	
	@GetMapping("/{id}")
	public ResponseDto<?> getPost(@PathVariable Long id,
			@RequestHeader("Authorization") String token) {
		return postService.getPost(id, token != null ? jwtTokenProvider.validateAndGetUserId(token.replace("Bearer ", "")) : null);
	}
	
	@PutMapping("/{id}")
	public ResponseDto<?> updatePost(
			@PathVariable Long id,
			@RequestBody PostUpdateRequest dto,
			@RequestHeader("Authorization") String token
//			HttpServletRequest request
			) {
//		String token = request.getHeader("Authorization");
		
		return postService.updatePost(token, id, dto);
	}
	
	@DeleteMapping("/{id}")
	public ResponseDto<?> deletePost(
			@PathVariable Long id,
			@RequestHeader("Authorization") String token
//			HttpServletRequest request
			) {
//		String token = request.getHeader("Authorization");
		
		return postService.deletePost(token, id);
	}
}
