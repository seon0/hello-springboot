package com.example.demo.domain.role.init;

import java.util.Set;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.role.entity.Role;
import com.example.demo.domain.role.repositoty.RoleRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.redis.TokenRedisService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleInitConfig implements ApplicationRunner {

	private final RoleRepository roleRepository;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final TokenRedisService tokenRedisService;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		Role userRole = createRole("ROLE_USER");
		createRole("ROLE_ADMIN");
		
		if ( ! userRepository.existsByEmail("test@gmail.com") ) {
			User user = User.builder()
					.email("test@gmail.com")
					.password(passwordEncoder.encode("pass"))
					.nickname("기본 유저")
					.roles(Set.of(userRole))
					.build();
			
			userRepository.save(user);
		}
		
		tokenRedisService.test();
	}

	private Role createRole(String roleName) {
		Role role = null;
		if ( ! roleRepository.existsByRoleName(roleName) ) {
			role= roleRepository.save( Role.builder().roleName(roleName).build() );
			System.out.println("[RoleInitializer] 생성됨: " + roleName);
		}
			
//		roleRepository.findByRoleName(roleName)
//		.orElseGet( 
//				() -> roleRepository.save( Role.builder().roleName(roleName).build() )
//		);
		return role;
	}
	
}
