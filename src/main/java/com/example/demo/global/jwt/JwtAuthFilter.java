package com.example.demo.global.jwt;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.error.UnauthorizedException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter{
	
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	
	/*  @RequiredArgsConstructor 설정을 추가하여 주석처리함. */
//	public JwtAuthFilter(JwtTokenProvider jwtTokenProvider) {
//		this.jwtTokenProvider = jwtTokenProvider;
//	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String token = resolveToken(request);
		System.out.println("[JwtAuthFilter - doFilterUnternal] token : " + token);
		
		if ( token != null ) {
			
			try {
				Long userId = jwtTokenProvider.validateAndGetUserId(token);
				
				Optional<User> userOpt = userRepository.findById(userId);
				
				if ( userOpt.isPresent() ) {
					User user = userOpt.get();
					// 인증 객체 생성
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
					
					auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); // 이 코드는 필요 없나????????
					SecurityContextHolder.getContext().setAuthentication(auth);
					
				}
				
			} catch (Exception e) {
				log.warn("JWT 인증 실패: {}", e.getMessage());
				throw new UnauthorizedException("유효하지 않은 토큰입니다.");
			}
		}
		
		filterChain.doFilter(request, response);
		
	}
	


	private String resolveToken(HttpServletRequest request) {
		String bearer = request.getHeader("Authorization");
		
		if ( bearer != null && bearer.startsWith("Bearer ") ) {
			return bearer.substring(7);
		}
		
		return null;
	}
	
	

}
