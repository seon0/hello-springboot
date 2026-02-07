package com.example.demo.global.redis;

import java.time.Duration;

//import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.domain.post.dto.PostDetailResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostRedisService {
	
//	@Qualifier("postRedisTemplate")
	private final RedisTemplate<String, PostDetailResponse> postRedisTemplate;
	
	private static final Duration TTL = Duration.ofMinutes(5);
	
	public PostDetailResponse getPost(Long postId) {
		String key = getKey(postId);
		return postRedisTemplate.opsForValue().get(key);
	}
	
	public void savePost(PostDetailResponse post) {
		String key = getKey(post.getId());
		postRedisTemplate.opsForValue().set(key, post, TTL);
	}
	
	public void deletePost(Long postId) {
		postRedisTemplate.delete(getKey(postId));
	}

	private String getKey(Long postId) {
		return "post::" + postId;
	}

}
