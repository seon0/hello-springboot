package com.example.demo.global.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

	@CreationTimestamp
	@Column(updatable = false)
	private LocalDateTime createdAt;
	
	
	@UpdateTimestamp
	private LocalDateTime modifiedAt;
}
