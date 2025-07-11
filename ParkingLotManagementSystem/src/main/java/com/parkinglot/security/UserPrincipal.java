package com.parkinglot.security;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.parkinglot.entity.ParkingManagerEntity;
import com.parkinglot.entity.RoleEntity;

@Component
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;

	private final transient ParkingManagerEntity manager;
	private final Long managerId;

	public UserPrincipal(ParkingManagerEntity manager) {
		this.manager = manager;
		this.managerId = this.manager.getId();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<>();
//		Collection<AuthorityEntity> authorityEntities = new HashSet<>();
		Collection<RoleEntity> roles = manager.getRoles();
		if (roles == null) {
			return authorities;
		}
		roles.forEach(roleEntity -> {
			authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));

			roleEntity.getAuthorities().forEach(authorityEntity -> {
				authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()));
			});
		});
		return authorities;
	}

	@Override
	public String getPassword() {
		return this.manager.getPassword();
	}

	@Override
	public String getUsername() {
		return this.manager.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getManagerId() {
		return managerId;
	}

	public String getMail() {
		return this.manager.getMail();
	}
}