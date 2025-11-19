package com.example.demo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.dto.ResponseDto;
import com.example.demo.dto.request.PostCreateRequest;
import com.example.demo.dto.request.PostUpdateRequest;
import com.example.demo.entity.Post;
import com.example.demo.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
	
	private final PostService postService;
	
	
	@GetMapping
	public List<Post> findAll() {
		return postService.getAllPost();
	}
	
	@GetMapping("/{id}")
	public ResponseDto<?> findOne(@PathVariable Long id) {
		Post post = postService.getPostById(id);
		return post != null ? ResponseDto.success(post) : ResponseDto.fail("해당 ID를 찾을 수 없습니다.");
	}
	
	@PostMapping
	public ResponseDto<?> create(@RequestBody PostCreateRequest request) {
//		Post saved = postService.createPost(requestDto);
//		return ResponseDto.success(saved);
		return ResponseDto.success(postService.create(request));
	}
	
	@PutMapping("/{id}")
	public ResponseDto<?> update(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
//		return postService.updatePost(id, requestDto);
		return ResponseDto.success(postService.update(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseDto<?> delete(@PathVariable Long id) {
		postService.deletePost(id);
		return ResponseDto.success("삭제완료");
	}
	
	
	@GetMapping("/paging")
	public Page<Post> paging(Pageable pageable) { // URL 파라미터로 들어오는 정보들은 paging에 
		return postService.getPosts(pageable);
	}
	
	@GetMapping("/search")
	public List<Post> search(@RequestParam String keyword) {
		return postService.searchByTitle(keyword);
	}
	

}
