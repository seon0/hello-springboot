package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
	
	@Email(message = "이메일 형식 오류")
	@NotBlank(message = "이메일을 입력하세요.")
	private String email;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	private String password;

}
