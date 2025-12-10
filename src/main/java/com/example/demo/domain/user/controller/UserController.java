package com.example.demo.domain.user.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.basic.dto.ResponseDto;
import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserLoginResponse;
import com.example.demo.domain.user.dto.UserMeResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.dto.UserJoinRequest;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.util.SecurityUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/join")
	public ResponseEntity<?> register(@Valid @RequestBody UserJoinRequest req)  {
		System.out.println("register:: email- "+req.getEmail() + ", nickname:" + req.getNickname());
		userService.join(req);
		return ResponseEntity.ok("회원가입 완료");
//		return ResponseDto.success(userService.join(req));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest req) {
		UserLoginResponse res = userService.login(req);
//		String token = userService.login(req.getEmail(), req.getPassword());
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> me() {
		Long loginUserId = SecurityUtil.getLoginUserId();
		if ( loginUserId == null ) {
			return ResponseEntity.status(401).build();
		}
		UserMeResponse res = userService.me(loginUserId);
		return ResponseEntity.ok(res);
	}
	
	/* Controller에서 UserId 읽는법 1 */
	public User meee(Authentication auth) {
//		Long userId = (Long) auth.getPrincipal();
//		return "userId = " + userId;
		return (User)auth.getPrincipal();
	}
	
	/* Controller에서 UserId 읽는법 2 */
	public Long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Long) auth.getPrincipal();
	}

}
