package com.example.demo.domain.user.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRoleUpdateRequest {

	private List<String> roles; // ["ROLE_USER", "ROLE_ADMIN"]
	
}
