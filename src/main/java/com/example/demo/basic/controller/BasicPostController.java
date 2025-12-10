package com.example.demo.basic.controller;

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

import com.example.demo.basic.dto.PostCreateRequest;
import com.example.demo.basic.dto.PostRequestDto;
import com.example.demo.basic.dto.PostResponseDto;
import com.example.demo.basic.dto.PostUpdateRequest;
import com.example.demo.basic.dto.ResponseDto;
import com.example.demo.basic.entity.Posts;
import com.example.demo.basic.service.BasicPostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/basic/posts")
public class BasicPostController {
	
	private final BasicPostService basicPostService;
	
	
	@GetMapping
	public List<Posts> findAll() {
		return basicPostService.getAllPost();
	}
	
	@GetMapping("/{id}")
	public ResponseDto<?> findOne(@PathVariable Long id) {
		Posts post = basicPostService.getPostById(id);
		return post != null ? ResponseDto.success(post) : ResponseDto.fail("해당 ID를 찾을 수 없습니다.");
	}
	
	@PostMapping
	public ResponseDto<?> create(@RequestBody PostCreateRequest request) {
//		Post saved = postService.createPost(requestDto);
//		return ResponseDto.success(saved);
		return ResponseDto.success(basicPostService.create(request));
	}
	
	@PutMapping("/{id}")
	public ResponseDto<?> update(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
//		return postService.updatePost(id, requestDto);
		return ResponseDto.success(basicPostService.update(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseDto<?> delete(@PathVariable Long id) {
		basicPostService.deletePost(id);
		return ResponseDto.success("삭제완료");
	}
	
	
	@GetMapping("/paging")
	public Page<Posts> paging(Pageable pageable) { // URL 파라미터로 들어오는 정보들은 paging에 
		return basicPostService.getPosts(pageable);
	}
	
	@GetMapping("/search")
	public List<Posts> search(@RequestParam String keyword) {
		return basicPostService.searchByTitle(keyword);
	}
	

}
