package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;
	
	
	@PostMapping
	public PostResponseDto create(@RequestBody PostRequestDto requestDto) {
		return postService.createPost(requestDto);
	}
	
	@GetMapping
	public List<Post> findAll() {
		return postService.getAllPost();
	}
	
	@GetMapping("/{id}")
	public Post findOne(@PathVariable Long id) {
		return postService.getPostById(id);
	}
	
	@PutMapping("/{id}")
	public PostResponseDto update(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
		return postService.updatePost(id, requestDto);
	}
	
	@DeleteMapping("/{id}")
	public String delete(@PathVariable Long id) {
		postService.deletePost(id);
		return "deleted";
	}
	
	
	

}
