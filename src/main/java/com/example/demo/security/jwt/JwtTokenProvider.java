package com.example.demo.security.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	private final Key key;
	
	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		this.key = new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}
	
	public String generateToken(Long userId) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + (1000L * 60 * 60 * 3)); // 3시간
		
		return Jwts.builder()
				.subject(String.valueOf(userId))
				.issuedAt(now)
				.expiration(expiry)
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
