package com.parkinglot.service;

import java.util.List;

import com.parkinglot.dto.BillResponseDTO;
import com.parkinglot.dto.ParkResponseDTO;
import com.parkinglot.dto.ParkingSpotDTO;
import com.parkinglot.dto.SpotStatusDTO;
import com.parkinglot.dto.TicketHistoryDTO;
import com.parkinglot.dto.VehicleRequestDTO;

public interface ParkingService {

	BillResponseDTO removeVehicle(String ticketId);

	List<ParkingSpotDTO> getAvailableSpots();

	ParkResponseDTO generateTicket(VehicleRequestDTO dto, String strategyName);

	String confirmPayment(String ticketId);

	List<SpotStatusDTO> getAllSpotStatus();

	List<TicketHistoryDTO> getSpotHistory(Long spotId);

//	void refreshSpots();
}