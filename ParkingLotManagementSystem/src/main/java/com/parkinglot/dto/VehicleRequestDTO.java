package com.parkinglot.dto;

import com.parkinglot.entity.VehicleType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VehicleRequestDTO {

	@NotBlank(message = "Vehicle number is required")
	private String vehicleNo;

	@NotNull(message = "Vehicle type is required")
	private VehicleType type;
}