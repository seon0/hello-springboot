package com.example.demo.global.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleInitConfig implements ApplicationRunner {

	private final RoleRepository roleRepository;


	@Override
	public void run(ApplicationArguments args) throws Exception {
		createRole("ROLE_USER");
		createRole("ROLE_ADMIN");
	}

	private void createRole(String roleName) {
		roleRepository.findByRoleName(roleName)
			.orElseGet( 
					() -> roleRepository.save( Role.builder().roleName(roleName).build() )
			);
	}
	
}
