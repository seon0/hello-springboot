package com.example.demo.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.UserJoinRequest;
import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserLoginResponse;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.util.SecurityUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	
	@PostMapping("/join")
	public ResponseEntity<?> join(@Valid @RequestBody UserJoinRequest req)  {
		log.info("[UserController - join] req info:: email- "+req.getEmail() + ", nickname:" + req.getNickname());
		userService.join(req);
		return ResponseEntity.ok("회원가입 완료");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest req) {
		UserLoginResponse res = userService.login(req);
		
		return ResponseEntity.ok(res);
	}
	
	@GetMapping("/me")
	public ResponseEntity<?> me() {
		Long loginUserId = SecurityUtil.getLoginUserId();
		if ( loginUserId == null ) {
			return ResponseEntity.status(401).build();
		}
		return ResponseEntity.ok(userService.me(loginUserId));
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
	
	@PostMapping("/logout")
	public ResponseEntity<?> logout(HttpServletRequest req) {
		userService.logout(req);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/refresh")
	public ResponseEntity<?> refresh(HttpServletRequest req) {
		return ResponseEntity.ok(userService.refresh(req));

	}
}
