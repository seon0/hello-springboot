package com.example.demo.domain.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {

	@NotBlank(message = "이메일을 입력하세요.")
	@Email(message = "이메일 형식이 올바르지 않습니다.")
	private String email;
	
	@NotBlank(message = "비밀번호를 입력하세요.")
	@Size(min = 6, message = "비밀번호는 최소 6자리 이상이어야 합니다.")
	private String password;
	
	@NotBlank(message = "이름을 입력하세요.")
	private String name;
}
