package com.parkinglot.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.parkinglot.dto.ManagerResponse;
import com.parkinglot.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}
	
	@Override
	public ManagerResponse findByUsername(String username) {
		return null;
	}
}