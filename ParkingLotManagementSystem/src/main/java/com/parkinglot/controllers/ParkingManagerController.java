package com.parkinglot.controllers;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.ManagerRequest;
import com.parkinglot.service.ManagerService;
import com.parkinglot.shared.Role;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parkingManager/")
@RequiredArgsConstructor
public class ParkingManagerController {

	private final ManagerService managerService;

	@PostMapping("register")
	public ResponseEntity<String> registerManager(@RequestBody ManagerRequest request) {
		request.setRoles(new HashSet<>(Arrays.asList(Role.ROLE_MANAGER.name())));
		boolean user = managerService.addUser(request);
		if (user) {
			return ResponseEntity.status(HttpStatus.CREATED).body("Created");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Some Exception");
	}
}