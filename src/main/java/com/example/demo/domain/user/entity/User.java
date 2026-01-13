package com.example.demo.domain.user.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.domain.role.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // 일단 이거 있어야함
@Table(name = "users")
public class User implements UserDetails{
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, length = 100)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false, unique = true, length = 30)
	private String nickname;
	
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	@Builder.Default
	private Set<Role> roles = new HashSet<>();
	
//	@ElementCollection(fetch = FetchType.EAGER)
//	@Enumerated(EnumType.STRING)
//	private Set<Role> roles = new HashSet<>();
	
	@Column(nullable = false)
	private boolean deleted = false;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime modifiedAt;
	
	@PrePersist
	public void onCreate() {
		this.createdAt = LocalDateTime.now();
		this.modifiedAt = LocalDateTime.now();
		if ( roles == null || roles.isEmpty() ) {
			roles = new HashSet<>();
			roles.add(Role.builder().id(1L).roleName("ROLE_USER").build());
		}
		
//		if ( this.deleted == false) this.deleted = false;
	}
	
	@PreUpdate
	public void onUpdate() {
		this.modifiedAt = LocalDateTime.now();
	}
	
	public void addRole(Role role) {
		this.roles.add(role);
	}
	
//	public void setRoles(Set<Role> roles) {
//		this.roles = roles;
//	}
	
	public void softDelete() {
		this.deleted = true;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(r -> (GrantedAuthority)r::getRoleName )
				.collect(Collectors.toList());
		
	}
	
	
	@Override
	public String getUsername() {
		return this.email;
	}
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
