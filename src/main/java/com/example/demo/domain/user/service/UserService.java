package com.example.demo.domain.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.role.entity.Role;
import com.example.demo.domain.role.repositoty.RoleRepository;
import com.example.demo.domain.user.controller.UserController;
import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserLoginResponse;
import com.example.demo.domain.user.dto.UserJoinRequest;
import com.example.demo.domain.user.dto.UserMeResponse;
import com.example.demo.domain.user.dto.UserResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.exception.DuplicateUserException;
import com.example.demo.domain.user.exception.UserNotFoundException;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.dto.ResponseDto;
import com.example.demo.global.error.CustomException;
import com.example.demo.global.error.ErrorCode;
import com.example.demo.global.error.NotFoundException;
import com.example.demo.global.jwt.JwtTokenProvider;
import com.example.demo.global.jwt.TokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

	private final RoleRepository roleRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
//	private final JwtTokenProvider jwtTokenProvider;
	private final TokenService tokenService;

	
	@Transactional
	public UserResponse join(UserJoinRequest request) {
		
		if ( userRepository.existsByEmail(request.getEmail()) ) {
			throw new DuplicateUserException("이미 가입된 이메일입니다.");
		}
		Role userRole = roleRepository.findByRoleName("ROLE_USER")
				.orElseThrow( () -> new CustomException(ErrorCode.BAD_REQUEST, "기본 권한이 없습니다.") ); // 
		
		User user = User.builder()
				.email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword()))
				.nickname(request.getNickname())
				.roles(Set.of(userRole))
				.build();
		
		userRepository.save(user);
		return UserResponse.from(user);
	}

	@Transactional
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
	
	@Transactional(readOnly = true)
	public UserLoginResponse login(UserLoginRequest req) {
//		User user = userRepository.findByEmailAndDeletedFalse(req.getEmail())
//								.orElseThrow( ()  -> new UserNotFoundException("이메일을 다시 확인해주세요.") );
//		if( ! passwordEncoder.matches(req.getPassword(), user.getPassword())) {
//			throw new CustomException(ErrorCode.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
//		}
//		System.out.println("login user [username: " + user.getNickname() + ", password: " +  user.getPassword() + " ]");
//		
////		Set<String> roles = user.getRoles()
////					.stream()
////					.map(Role::getRoleName)
////					.collect(Collectors.toSet());
//		String token = jwtTokenProvider.createToken(user.getId());
//		
//		return new UserLoginResponse(user.getId(), user.getNickname(), token, "Bearer "+token);
		
		return tokenService.login(req);
	}
	
	
	public UserMeResponse me(Long loginUserId) {
		User user = userRepository.findByIdAndDeletedFalse(loginUserId)
				.orElseThrow( () -> new UserNotFoundException("존재하지 않는 회원입니다."));
		
		Set<String> roles = user.getRoles().stream()
					.map(Role::getRoleName)
					.collect(Collectors.toSet());
		
//		return UserMeResponse.builder()
//				.id(user.getId())
//				.email(user.getEmail())
//				.nickname(user.getNickname())
//				.roles(roles)
//				.build();
		return UserMeResponse.from(user);
	}
	
}
