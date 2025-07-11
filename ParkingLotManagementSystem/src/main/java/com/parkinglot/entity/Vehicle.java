package com.parkinglot.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Vehicle {
	@Id
	private String vehicleId;

	private String vehicleNo;

	@Enumerated(EnumType.STRING)
	private VehicleType type;
}