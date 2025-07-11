package com.parkinglot.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.parkinglot.dto.VehicleHistoryDTO;
import com.parkinglot.entity.Ticket;
import com.parkinglot.repository.TicketRepository;
import com.parkinglot.service.VehicleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

	private final TicketRepository ticketRepository;

	@Override
	public List<VehicleHistoryDTO> getVehicleHistory(String vehicleNo) {
		List<Ticket> tickets = ticketRepository.findByVehicle_VehicleNoOrderByEntryTimeDesc(vehicleNo);
		return tickets.stream().map(VehicleHistoryDTO::new).toList();
	}
}