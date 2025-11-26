package com.example.demo.domain.user;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserRegisterRequest;
import com.example.demo.domain.user.dto.UserSignupRequest;
import com.example.demo.dto.ResponseDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	@PostMapping("/register")
	public ResponseDto<?> register(@Valid @RequestBody UserRegisterRequest req)  {
		System.out.println("register:: email- "+req.getEmail() + ", name:" + req.getName());
		return ResponseDto.success(userService.register(req));
	}
	
	@PostMapping("/login")
	public ResponseDto<?> login(@Valid @RequestBody UserLoginRequest req) {
		String token = userService.login(req.getEmail(), req.getPassword());
		
		return ResponseDto.success(
				Map.of("token", token)
		);
	}
	
	@PostMapping("/signup")
	public ResponseDto<?> signup(@Valid @RequestBody UserSignupRequest request) {
		return ResponseDto.success(userService.signup(request));
	}
	
	
	
	/* Controller에서 UserId 읽는법 1 */
	@GetMapping("/me")
	public String me(Authentication auth) {
		Long userId = (Long) auth.getPrincipal();
		return "userId = " + userId;
	}
	
	/* Controller에서 UserId 읽는법 2 */
	public Long getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return (Long) auth.getPrincipal();
	}

}
