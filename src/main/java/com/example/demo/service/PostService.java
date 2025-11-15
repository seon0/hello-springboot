package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.dto.PostRequestDto;
import com.example.demo.dto.PostResponseDto;

@Service
public class PostService {
	
	private Map<Long, PostResponseDto> store = new HashMap<>();
	private Long sequence = 1L;
	
	public PostResponseDto createPost(PostRequestDto requestDto) {
		PostResponseDto post = new PostResponseDto(
				sequence, 
				requestDto.getTitle(), 
				requestDto.getContent()
		);
		store.put(sequence, post);
		sequence++;
		
		return post;
	}
	
	public List<PostResponseDto> getAllPost() {
		return new ArrayList<>(store.values());
	}
	
	public PostResponseDto getPostById(Long id) {
		return store.get(id);
	}
	
	public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
		PostResponseDto existing = store.get(id);
		if( existing == null ) 
			return null;
		
		existing.setTitle(requestDto.getTitle());
		existing.setContent(requestDto.getContent());
		return existing;
	}
	
	public void deletePost(Long id) {
		store.remove(id);
	}

}
