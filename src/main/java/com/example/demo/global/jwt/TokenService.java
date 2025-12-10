package com.example.demo.global.jwt;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserLoginResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.error.UnauthorizedException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TokenService {
	
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	/*
	 * Header에서 Authorization 값을 꺼내 유저 반환
	 */
	public User getUserFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if ( token == null || token.isEmpty() ) {
			throw new RuntimeException("Authorization header 가 없습니다.");
		}
		
		return getUserFromToken(token);
	}
	
	public User getUserFromToken(String token) {
		
		// "Bearer xxx" 형태일 경우, 가공
		if ( token.startsWith("Bearer ") ) {
			token = token.substring(7);
		}
		
//		//토큰 유효성 검증
//		if ( ! jwtTokenProvider.validateToken(token) ) {
//			throw new RuntimeException("유효하지 않은 토큰입니다.");
//		}
		
		Long userId = jwtTokenProvider.validateAndGetUserId(token);
		
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));
		
	}

	
	public UserLoginResponse login(UserLoginRequest req) {
		User user = userRepository.findByEmailAndDeletedFalse(req.getEmail())
				.orElseThrow( () -> new UnauthorizedException("잘못된 이메일 입니다."));
		
		if ( ! passwordEncoder.matches(req.getPassword(), user.getPassword()) ) {
			throw new UnauthorizedException("잘못된 이메일 또는 비밀번호입니다.");
		}
		
		String token = jwtTokenProvider.createToken(user.getId());
		
		return new UserLoginResponse(user.getId(), user.getNickname(), token, "Bearer " + token);
	}
}
