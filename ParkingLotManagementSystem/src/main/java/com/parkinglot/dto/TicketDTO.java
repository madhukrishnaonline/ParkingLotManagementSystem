package com.parkinglot.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.Vehicle;

import lombok.Data;

@Data
public class TicketDTO {
	private String id;
	@JsonFormat(pattern = "yyyy-mm-ddThh:MM:ss")
	private LocalDateTime entryTime;
	private ParkingSpot spot;
	private Vehicle vehicle;
}