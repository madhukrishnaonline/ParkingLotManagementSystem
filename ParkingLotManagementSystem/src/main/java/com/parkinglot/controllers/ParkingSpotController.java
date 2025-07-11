package com.parkinglot.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.ParkingSpotDTO;
import com.parkinglot.dto.SpotStatusDTO;
import com.parkinglot.dto.TicketHistoryDTO;
import com.parkinglot.service.ParkingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/spots")
public class ParkingSpotController {

	private final ParkingService parkingSpotService;

	@GetMapping("/available")
	public ResponseEntity<List<ParkingSpotDTO>> getAvailableSpots() {
		List<ParkingSpotDTO> availableSpots = parkingSpotService.getAvailableSpots();
		return availableSpots.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(availableSpots);
	}

	@GetMapping("/status")
	public ResponseEntity<List<SpotStatusDTO>> getAllSpotStatus() {
		List<SpotStatusDTO> spotStatus = parkingSpotService.getAllSpotStatus();
		return spotStatus.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(spotStatus);
	}

	@GetMapping("/history/{spotId}")
	public ResponseEntity<List<TicketHistoryDTO>> getSpotHistory(@PathVariable Long spotId) {
		List<TicketHistoryDTO> spotHistory = parkingSpotService.getSpotHistory(spotId);
		return spotHistory.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(spotHistory);
	}

}