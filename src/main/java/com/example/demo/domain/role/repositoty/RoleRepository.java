package com.example.demo.domain.role.repositoty;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.role.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Optional<Role> findByRoleName(String roleName);
	boolean existsByRoleName(String roleName);

}
