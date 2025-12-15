package com.example.demo.domain.post.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.post.dto.PostRequestDto;
import com.example.demo.domain.post.dto.PostResponse;
import com.example.demo.domain.post.dto.PostSearchCondition;
import com.example.demo.domain.post.dto.PostUpdateRequest;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.service.PostService;
import com.example.demo.domain.user.entity.User;
import com.example.demo.dto.ResponseDto;
import com.example.demo.global.jwt.JwtTokenProvider;
import com.querydsl.core.types.Ops.DateTimeOps;

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

//	@GetMapping("/search")
	public ResponseDto<?> search(
			@RequestBody String keyword,
			@RequestBody String content,
			@RequestBody String nickname,
			@RequestBody LocalDate fromDate,
			@RequestBody LocalDate toDate,
			@RequestBody Pageable pageable,
			HttpServletRequest request
	) {

		PostSearchCondition cond = new PostSearchCondition(keyword, content, nickname, fromDate, toDate);
		return ResponseDto.success(postService.search(cond, pageable));
	}
	
//	@GetMapping("/search")
	public ResponseDto<?> searchPosts(
			@RequestBody PostSearchCondition condition, HttpServletRequest request
	) {
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		Long userId = jwtTokenProvider.validateAndGetUserId(token);
		
		return ResponseDto.success(postService.searchPosts(condition, userId));
	}
	
	@GetMapping("/search")
	public ResponseDto<Page<PostResponse>> searchFinal(
			PostSearchCondition cond,
			Pageable pageable,
			Authentication auth
	) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return ResponseDto.success(postService.searchFinal(cond, pageable, userId));
	}
	
}
