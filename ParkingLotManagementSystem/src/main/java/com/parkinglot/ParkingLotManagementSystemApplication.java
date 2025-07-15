package com.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.parkinglot.entity.ParkingManagerEntity;

@SpringBootApplication
public class ParkingLotManagementSystemApplication {

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	ParkingManagerEntity entity() {
		return new ParkingManagerEntity();
	}
	
	@Bean
	SpringApplicationContext springApplicationContext() {
		return new SpringApplicationContext();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ParkingLotManagementSystemApplication.class, args);
	}
}