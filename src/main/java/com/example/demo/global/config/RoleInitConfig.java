package com.example.demo.global.config;

import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.RoleRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleInitConfig implements ApplicationRunner {

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		createRole("ROLE_USER");
		createRole("ROLE_ADMIN");
		Role role = new Role();
		role.setId(1L);
		role.setRoleName("ROLE_USER");
		User user = User.builder()
				.email("test@gmail.com")
				.password(passwordEncoder.encode("pass"))
				.name("기본 유저")
				.roles(Set.of(role))
				.build();
		
		User saved = userRepository.save(user);
	}

	private void createRole(String roleName) {
		roleRepository.findByRoleName(roleName)
			.orElseGet( 
					() -> roleRepository.save( Role.builder().roleName(roleName).build() )
			);
	}
	
}
