package com.parkinglot.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.BillResponseDTO;
import com.parkinglot.service.impl.ParkingServiceImpl;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/exit")
@RequiredArgsConstructor
public class ExitGateController {

	private final ParkingServiceImpl parkingService;

	@PostMapping("/out/{ticketId}")
	public ResponseEntity<BillResponseDTO> processExit(@PathVariable String ticketId) {
		BillResponseDTO billResponse = parkingService.removeVehicle(ticketId);
		return billResponse.getTicketId() != null ? ResponseEntity.ok(billResponse) : ResponseEntity.notFound().build();
	}

	@PostMapping("/pay/{ticketId}")
	public ResponseEntity<String> markTicketAsPaid(@PathVariable String ticketId) {
		String ticket = parkingService.confirmPayment(ticketId);
		return ticket != null ? ResponseEntity.ok("Payment received for ticket: " + ticket)
				: ResponseEntity.notFound().build();
	}
}