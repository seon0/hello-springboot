package com.example.demo.domain.comment.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.comment.dto.CommentCreateRequest;
import com.example.demo.domain.comment.dto.CommentResponse;
import com.example.demo.domain.comment.dto.CommentUpdateRequest;
import com.example.demo.domain.comment.service.CommentService;
import com.example.demo.domain.user.entity.User;
import com.example.demo.dto.ResponseDto;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts/{postId}/comments")
public class CommentController {
	
	private final CommentService commentService;
	
		
	@GetMapping
	public List<CommentResponse> getComments(@PathVariable Long postId) {
		return commentService.getComments(postId);
	}
	
	@PostMapping
	public CommentResponse createComment(
			@PathVariable Long postId,
			@RequestBody CommentCreateRequest request,
			Authentication auth
			) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return commentService.createComment(postId, userId, request);
	}
	
	@PutMapping("{commentId}")
	public CommentResponse updateComment(
			@PathVariable Long postId,
			@PathVariable Long commentId,
			@RequestBody CommentUpdateRequest request,
			Authentication auth
			) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		return commentService.updateComment(commentId, userId, request);
	}

	@DeleteMapping("/{commentId}")
	public ResponseDto<?> deleteComment (
			@PathVariable Long postId,
			@PathVariable Long commentId,
			Authentication auth) {
		Long userId = ( (User)auth.getPrincipal() ).getId();
		commentService.deleteComment(commentId, userId);
		return ResponseDto.success("OK");
	}
}
