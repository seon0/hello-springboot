package com.example.demo.domain.user;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserRegisterRequest;
import com.example.demo.domain.user.dto.UserResponse;
import com.example.demo.domain.user.dto.UserSignupRequest;
import com.example.demo.dto.ResponseDto;
import com.example.demo.global.exception.CustomException;
import com.example.demo.security.jwt.JwtTokenProvider;

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
				.username(request.getName())
				.roles(Set.of(userRole))
				.build();
		
		User saved = userRepository.save(user);
		return UserResponse.builder()
				.id(saved.getId())
				.email(saved.getEmail())
				.name(saved.getUsername())
				.roles(saved.getRoles().stream()
						.map(Role::getRoleName)
						.collect(Collectors.toSet())
				)
				.build();
	}

	public void updateUserRoles(Long userId, List<String> roleNames) {
		User user = userRepository.findById(userId)
				.orElseThrow( () -> new RuntimeException("유저 없음") );
		List<Role> roles = roleNames.stream()
				.map( name -> roleRepository.findByRoleName(name)
						.orElseThrow( () -> new RuntimeException("권한 없음:" + name) ) )
				.toList();
		
		user.setRoles(new HashSet<>(roles));
		userRepository.save(user);
	}
	
	public String login(String email, String password) {
		User user = userRepository.findByEmail(email)
								.orElseThrow( ()  -> new CustomException("존재하지 않는 이메일입니다.") );
		if( ! passwordEncoder.matches(password, user.getPassword())) {
			throw new CustomException("비밀번호가 일치하지 않습니다.");
		}
		System.out.println("login user [username: " + user.getUsername() + ", password: " +  user.getPassword() + " ]");
		
		Set<String> roles = user.getRoles()
					.stream()
					.map(Role::getRoleName)
					.collect(Collectors.toSet());
		
		return jwtTokenProvider.generateToken(user.getId());
	}
	
	public ResponseDto<?> signup(UserSignupRequest request) {
		
		if ( userRepository.existsByEmail(request.getEmail()) ) {
			return ResponseDto.fail("이미 존재하는 이메일입니다.");
		}
		
		User user = User.builder()
				.email(request.getEmail())
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.build();
		
		userRepository.save(user);
		
		return ResponseDto.success("회원가입 완료");
	}
	
	
}
