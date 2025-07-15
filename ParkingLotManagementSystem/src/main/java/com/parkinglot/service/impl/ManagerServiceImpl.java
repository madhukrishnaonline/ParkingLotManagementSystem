package com.parkinglot.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.parkinglot.dto.ManagerRequest;
import com.parkinglot.dto.ManagerResponse;
import com.parkinglot.entity.ParkingManagerEntity;
import com.parkinglot.entity.RoleEntity;
import com.parkinglot.exception.ManagerNotFoundException;
import com.parkinglot.repository.ParkingManagerRepository;
import com.parkinglot.repository.RoleRepository;
import com.parkinglot.security.SecurityConstants;
import com.parkinglot.security.UserPrincipal;
import com.parkinglot.service.ManagerService;
import com.parkinglot.singleton.SingletonModelMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService {

	private final ParkingManagerRepository parkingManagerRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	private final RoleRepository roleRepository;

	@Override
	public boolean addUser(ManagerRequest request) {
		ParkingManagerEntity entity = SingletonModelMapper.mapData(request, ParkingManagerEntity.class);
		String passowrd = passwordEncoder.encode(request.getPassword());
		entity.setPassword(passowrd);

		Collection<RoleEntity> roleEntities = new ArrayList<RoleEntity>();
		request.getRoles().forEach(role -> {
			RoleEntity roleEntity = roleRepository.findByName(role);
			if (roleEntity != null) {
				roleEntities.add(roleEntity);
			}
		});
		entity.setRoles(roleEntities);
		ParkingManagerEntity managerEntity = parkingManagerRepository.save(entity);
		return managerEntity != null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ParkingManagerEntity entity = parkingManagerRepository.findByUsername(username)
				.orElseThrow(() -> new ManagerNotFoundException(SecurityConstants.MANAGER_NOT_FOUND));
		return new UserPrincipal(entity);
	}

	@Override
	public ManagerResponse findByUsername(String username) {
		ParkingManagerEntity entity = parkingManagerRepository.findByUsername(username)
				.orElseThrow(() -> new ManagerNotFoundException(""));
		return SingletonModelMapper.mapData(entity, ManagerResponse.class);
	}
}