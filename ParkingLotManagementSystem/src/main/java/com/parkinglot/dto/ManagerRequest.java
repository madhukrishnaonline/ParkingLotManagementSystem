package com.parkinglot.dto;

import java.util.Collection;

import lombok.Data;

@Data
public class ManagerRequest {
	private Long id;
	private String username;
	private String password;
	private String fullName;
	private Long mobile;
	private String mail;
	private Collection<String> roles;
}