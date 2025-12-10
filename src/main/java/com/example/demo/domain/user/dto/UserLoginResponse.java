package com.example.demo.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserLoginResponse {
	private Long userId;
	private String nickname;
	private String token; //JWT
	private String bearerToken; // (Swagger 편의) "Bearer xxx..." 형태로 제공

}
