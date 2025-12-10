package com.example.demo.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmailAndDeletedFalse(String email);
	Optional<User> findByIdAndDeletedFalse(Long id);
	boolean existsByEmail(String email);
	boolean existsByNickname(String nickname);
}
