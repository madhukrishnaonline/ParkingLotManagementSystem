package com.parkinglot.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponseDTO {
	private String ticketId;
	private String vehicleNo;
	private String vehicleType;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private long totalMinutes;
	private int price;
	private String message;
}