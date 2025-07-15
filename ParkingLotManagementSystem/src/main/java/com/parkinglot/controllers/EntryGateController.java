package com.parkinglot.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.ParkResponseDTO;
import com.parkinglot.dto.VehicleRequestDTO;
import com.parkinglot.exception.TicketCreationException;
import com.parkinglot.service.ParkingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/entry")
public class EntryGateController {

	private final ParkingService parkingService;

	@PostMapping("/park")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ParkResponseDTO> allowEntry(@Valid @RequestBody VehicleRequestDTO dto,
			@RequestParam(defaultValue = "defaultEntrance") String strategy) throws TicketCreationException {
		ParkResponseDTO responseTicket = parkingService.generateTicket(dto, strategy);

		return responseTicket != null ? ResponseEntity.status(HttpStatus.CREATED).body(responseTicket)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
}