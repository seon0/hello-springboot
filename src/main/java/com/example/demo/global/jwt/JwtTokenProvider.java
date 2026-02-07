package com.example.demo.global.jwt;

import java.nio.charset.StandardCharsets;
//import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtTokenProvider {
	
//	private final Key key;
//	
//	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
//		this.key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
//	}
	
	
	@Value("${jwt.secret}") 
	private String secret;
	
	private SecretKey key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}
	
	
	public String createToken(Long userId) {
//		Date now = new Date();
//		Date expiry = new Date(now.getTime() + (1000L * 60 * 60 * 3)); // 3시간
		
		long now = System.currentTimeMillis();
		
		return Jwts.builder()
				.subject(String.valueOf(userId))
				.issuedAt(new Date(now))
				.expiration(new Date(now + (1000L * 60 * 60 * 24) )) // 24시간
				.signWith(key)
				.compact();
	}
	
	// 토큰 검증
	public Long validateAndGetUserId(String token) {
		String userId = Jwts.parser()
				.verifyWith((SecretKey) key)
				.build()
				.parseSignedClaims(token)
				.getPayload()
				.getSubject();
				
		return Long.valueOf(userId);
	}
	
	

}
