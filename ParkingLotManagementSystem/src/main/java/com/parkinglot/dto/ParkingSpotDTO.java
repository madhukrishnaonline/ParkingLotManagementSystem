package com.parkinglot.dto;

import lombok.Data;

@Data
public class ParkingSpotDTO {
	private Long id;
	private String spotType;
	private boolean empty;
	private String vehicleId;
	private double price;
}