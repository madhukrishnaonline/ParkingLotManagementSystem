package com.parkinglot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.parkinglot.entity.AuthorityEntity;
import com.parkinglot.entity.ParkingManagerEntity;
import com.parkinglot.entity.RoleEntity;
import com.parkinglot.repository.AuthorityRepository;
import com.parkinglot.repository.ParkingManagerRepository;
import com.parkinglot.repository.RoleRepository;
import com.parkinglot.security.AppProperties;
import com.parkinglot.shared.Role;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class InitialSetup {

	private final ParkingManagerRepository parkingManagerRepository;
	private final RoleRepository roleRepository;
	private final AuthorityRepository authorityRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	private final AppProperties properties;

	@Transactional
	@EventListener
	public void setUpAdmin(ApplicationReadyEvent event) {
		log.info("InitialSetUp.setUpAdmin()" + event.getClass().getName());

		AuthorityEntity readAuthority = saveAuthorities("read_authority");
		AuthorityEntity writeAuthority = saveAuthorities("write_authority");
		AuthorityEntity deleteAuthority = saveAuthorities("delete_authority");

		saveRoles(Role.ROLE_MANAGER.name(), new ArrayList<>(Arrays.asList(readAuthority, writeAuthority)));
		RoleEntity roleAdmin = saveRoles(Role.ROLE_ADMIN.name(),
				new ArrayList<>(Arrays.asList(readAuthority, writeAuthority, deleteAuthority)));

		if (roleAdmin == null) {
			log.error("Admin Role is Null,cannot proceed");
			return;
		}
		ParkingManagerEntity admin = new ParkingManagerEntity();
		admin.setUsername("madhukrishnaonline");
		admin.setFullName("Madhu Krishna");
		admin.setPassword(passwordEncoder.encode(properties.getDefaultAdminPswrd()));
		admin.setMail(properties.getAdminMail());
		admin.setMobile(9876543210L);
		admin.setRoles(new ArrayList<>(Arrays.asList(roleAdmin)));

		if (!parkingManagerRepository.existsByMail(admin.getMail())) {
			parkingManagerRepository.save(admin);
		}
	}

	private RoleEntity saveRoles(String name, Collection<AuthorityEntity> authorityEntities) {
		RoleEntity roleEntity = roleRepository.findByName(name);
		if (roleEntity == null) {
			roleEntity = new RoleEntity(name);
			roleEntity.setAuthorities(authorityEntities);
			return roleRepository.save(roleEntity);
		}
		return roleEntity;
	}

	private AuthorityEntity saveAuthorities(String name) {
		AuthorityEntity authorityEntity = authorityRepository.findByName(name);
		if (authorityEntity == null) {
			authorityEntity = new AuthorityEntity(name);
			return authorityRepository.save(authorityEntity);
		}
		return authorityEntity;
	}
}