package com.example.demo.service;

import java.util.List;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;
import com.example.demo.dto.request.PostCreateRequest;
import com.example.demo.dto.request.PostUpdateRequest;
import com.example.demo.dto.response.PostResponse;
import com.example.demo.entity.Post;
import com.example.demo.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
	
	private final PostRepository postRepository;
	/*
	private Map<Long, PostResponseDto> store = new HashMap<>();
	private Long sequence = 1L;
	*/
	
//	public Post createPost(PostRequestDto requestDto) {
	public PostResponse create(PostCreateRequest request) {
		/*
		PostResponseDto post = new PostResponseDto(
				sequence, 
				requestDto.getTitle(), 
				requestDto.getContent()
		);
		store.put(sequence, post);
		sequence++;
		*/
		Post post = new Post();
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		
		Post saved = postRepository.save(post);
		
//		return saved;
		return new PostResponse(post);
	}
	
	// READ ALL
	public List<Post> getAllPost() {
//		return new ArrayList<>(store.values());
		return postRepository.findAll();
	}
	
	// READ ONE
	public Post getPostById(Long id) {
//		return store.get(id);
		return postRepository.findById(id).orElse(null);
	}
	
	//UPDATE
//	public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
	public PostResponse update(Long id, PostUpdateRequest request) {
//		PostResponseDto existing = store.get(id);
		Post existing = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID"));
//				.orElse(null);
//		if( existing == null ) 
//			return null;
		
		existing.setTitle(request.getTitle());
		existing.setContent(request.getContent());
		
//		Post updated = postRepository.save(existing);
//		return new PostResponseDto(updated.getId(), updated.getTitle(), updated.getContent());
		return new PostResponse(postRepository.save(existing));
	}
	
	//DELETE
	public void deletePost(Long id) {
//		store.remove(id);
		postRepository.deleteById(id);
	}
	
	public Page<Post>getPosts(Pageable pageable) {
		return postRepository.findAll(pageable);
	}
	
	public List<Post> searchByTitle(String keyword) {
		return postRepository.findByTitleContaining(keyword);
	}

}
