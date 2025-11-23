package com.example.demo.domain.role;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByRoleName(String roleName);

}
