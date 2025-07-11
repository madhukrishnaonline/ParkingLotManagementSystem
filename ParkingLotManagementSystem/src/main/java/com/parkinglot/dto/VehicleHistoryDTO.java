package com.parkinglot.dto;

import java.time.LocalDateTime;

import com.parkinglot.entity.Ticket;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehicleHistoryDTO {
	private String ticketId;
	private Long spotId;
	private String vehicleNo;
	private String vehicleType;
	private LocalDateTime entryTime;
	private LocalDateTime exitTime;
	private Integer price;
	private boolean paid;

	public VehicleHistoryDTO(Ticket ticket) {
		this.ticketId = ticket.getId();
		this.vehicleNo = ticket.getVehicle().getVehicleNo();
		this.vehicleType = ticket.getVehicle().getType().name();
		this.entryTime = ticket.getEntryTime();
		this.exitTime = ticket.getExitTime();
		this.price = ticket.getPrice();
		this.paid = ticket.isPaid();
		this.spotId = ticket.getSpot() != null ? ticket.getSpot().getId() : null;
	}
}