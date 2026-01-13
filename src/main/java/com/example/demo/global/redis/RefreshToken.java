package com.example.demo.global.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@RedisHash(value = "refreshToken", timeToLive = 60*60*24*7 ) // 7일
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
	
	@Id
	private String token;
	
	private Long userId;
	

}
