package com.example.demo.global.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenRedisService {
	
	private final RedisTemplate<String, Object> redisTemplate;
	
	public void test() {
		redisTemplate.opsForValue().set("hello", "redis");
		Object value = redisTemplate.opsForValue().get("hello");
		System.out.println("Redis value = " + value);
	}
	
	
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public void saveRefreshToken(String token, Long userId) {
		refreshTokenRepository.save(new RefreshToken(token, userId));
	}
	
	public Long getUserIdByToken(String token) {
		return refreshTokenRepository.findById(token)
						.map(RefreshToken::getUserId)
						.orElse(null);
	}
	
	public void delete(String token) {
		refreshTokenRepository.deleteById(token);
	}

}
