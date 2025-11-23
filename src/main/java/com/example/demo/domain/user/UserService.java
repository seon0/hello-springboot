package com.example.demo.domain.user;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserRegisterRequest;
import com.example.demo.domain.user.dto.UserResponse;
import com.example.demo.global.exception.CustomException;
import com.example.demo.global.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtTokenProvider jwtTokenProvider;
	
	public UserResponse register(UserRegisterRequest request) {
		
		if ( userRepository.existsByEmail(request.getEmail()) ) {
			throw new CustomException("이미 가입된 이메일입니다.");
		}
		Role userRole = roleRepository.findByRoleName("ROLE_USER")
				.orElseThrow( () -> new CustomException("기본 권한이 없습니다.") );
		
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.name(request.getName())
				.roles(Set.of(userRole))
				.build();
		
		User saved = userRepository.save(user);
		return UserResponse.builder()
				.id(saved.getId())
				.email(saved.getEmail())
				.name(saved.getName())
				.roles(saved.getRoles().stream()
						.map(Role::getRoleName)
						.collect(Collectors.toSet())
				)
				.build();
	}

	
	public String login(UserLoginRequest req) {
		User user = userRepository.findByEmail(req.getEmail())
								.orElseThrow( ()  -> new CustomException("존재하지 않는 이메일입니다.") );
		if( ! passwordEncoder.matches(req.getPassword(), user.getPassword())) {
			throw new CustomException("비밀번호가 일치하지 않습니다.");
		}
		
		Set<String> roles = user.getRoles()
					.stream()
					.map(Role::getRoleName)
					.collect(Collectors.toSet());
		
		return jwtTokenProvider.createToken(user.getEmail(), roles);
	}
	
}
