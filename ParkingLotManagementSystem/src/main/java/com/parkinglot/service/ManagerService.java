package com.parkinglot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.parkinglot.dto.ManagerResponse;

public interface ManagerService extends UserDetailsService{

	ManagerResponse findByUsername(String username);

}