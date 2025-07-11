package com.parkinglot.dto;

import lombok.Data;

@Data
public class ManagerResponse {
	private Long id;
	private String username;
	private String password;
	private String fullName;
	private Long mobile;
	private String mail;
}