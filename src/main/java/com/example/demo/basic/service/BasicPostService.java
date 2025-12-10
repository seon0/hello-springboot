package com.example.demo.basic.service;

import java.util.List;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.basic.dto.PostCreateRequest;
import com.example.demo.basic.dto.PostRequestDto;
import com.example.demo.basic.dto.PostResponse;
import com.example.demo.basic.dto.PostResponseDto;
import com.example.demo.basic.dto.PostUpdateRequest;
import com.example.demo.basic.entity.Posts;
import com.example.demo.basic.repository.PostsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BasicPostService {
	
	private final PostsRepository postsRepository;
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
		Posts post = new Posts();
		post.setTitle(request.getTitle());
		post.setContent(request.getContent());
		
		Posts saved = postsRepository.save(post);
		
//		return saved;
		return new PostResponse(post);
	}
	
	// READ ALL
	public List<Posts> getAllPost() {
//		return new ArrayList<>(store.values());
		return postsRepository.findAll();
	}
	
	// READ ONE
	public Posts getPostById(Long id) {
//		return store.get(id);
		return postsRepository.findById(id).orElse(null);
	}
	
	//UPDATE
//	public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
	public PostResponse update(Long id, PostUpdateRequest request) {
//		PostResponseDto existing = store.get(id);
		Posts existing = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 ID"));
//				.orElse(null);
//		if( existing == null ) 
//			return null;
		
		existing.setTitle(request.getTitle());
		existing.setContent(request.getContent());
		
//		Post updated = postRepository.save(existing);
//		return new PostResponseDto(updated.getId(), updated.getTitle(), updated.getContent());
		return new PostResponse(postsRepository.save(existing));
	}
	
	//DELETE
	public void deletePost(Long id) {
//		store.remove(id);
		postsRepository.deleteById(id);
	}
	
	public Page<Posts>getPosts(Pageable pageable) {
		return postsRepository.findAll(pageable);
	}
	
	public List<Posts> searchByTitle(String keyword) {
		return postsRepository.findByTitleContaining(keyword);
	}

}
