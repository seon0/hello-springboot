package com.example.demo.global.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	@Value("${jwt.expiration}")
	private long expiration;
	
	//JWT 생성
	public String createToken(String email, Set<String> roles) {
		Date now = new Date();
		Date expireDate = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
					.setSubject(email)
					.claim("roles", roles)
					.setIssuedAt(now)
					.setExpiration(expireDate)
					.signWith(SignatureAlgorithm.HS256, secretKey.getBytes(StandardCharsets.UTF_8))
					.compact();
	}
	
	public String getEmail(String token) {
		return Jwts.parser()
					.setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
					.parseClaimsJws(token)
					.getBody()
					.getSubject();
	}

}
