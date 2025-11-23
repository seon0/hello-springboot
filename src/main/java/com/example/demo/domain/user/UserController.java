package com.example.demo.domain.user;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.UserLoginRequest;
import com.example.demo.domain.user.dto.UserRegisterRequest;
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
		String token = userService.login(req);
		return ResponseDto.success(token);
	}

}
