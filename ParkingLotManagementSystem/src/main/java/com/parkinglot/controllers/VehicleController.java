package com.parkinglot.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parkinglot.dto.VehicleHistoryDTO;
import com.parkinglot.service.VehicleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vehicle")
public class VehicleController {

	private final VehicleService service;

	@GetMapping("/history/{vehicleNo}")
	public ResponseEntity<List<VehicleHistoryDTO>> getVehcileHistory(@PathVariable String vehicleNo) {
		List<VehicleHistoryDTO> list = service.getVehicleHistory(vehicleNo);
		return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
	}
}