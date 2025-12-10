package com.example.demo.domain.user.dto;

import java.util.Set;

import com.example.demo.domain.role.entity.Role;
import com.example.demo.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMeResponse {
	
	private Long id;
	
	private String email;
	
	private String nickname;
	
	private Set<String> roles;

	
	public static UserMeResponse from(User user) {
		return UserMeResponse.builder()
				.id(user.getId())
				.email(user.getEmail())
				.nickname(user.getNickname())
//				.roles(user.getRoles())
				.build();
	}
	
	
}
