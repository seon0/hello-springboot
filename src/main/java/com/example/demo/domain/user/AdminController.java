package com.example.demo.domain.user;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.AdminRoleUpdateRequest;
import com.example.demo.dto.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
	
	private final UserService userService;
	
	public ResponseDto<?> updateUserRoles (
			@PathVariable Long id,
			@RequestBody AdminRoleUpdateRequest request) {
		userService.updateUserRoles(id, request.getRoles());
		return ResponseDto.success("권한 변경 완료");
		
	}

}
