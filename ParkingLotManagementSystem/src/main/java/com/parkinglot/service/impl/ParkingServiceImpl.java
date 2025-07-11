package com.parkinglot.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.parkinglot.dto.BillResponseDTO;
import com.parkinglot.dto.ParkResponseDTO;
import com.parkinglot.dto.ParkingSpotDTO;
import com.parkinglot.dto.SpotStatusDTO;
import com.parkinglot.dto.TicketHistoryDTO;
import com.parkinglot.dto.VehicleRequestDTO;
import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.Ticket;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.entity.VehicleType;
import com.parkinglot.exception.NoParkingSpotAvailableException;
import com.parkinglot.exception.TicketCreationException;
import com.parkinglot.exception.TicketNotFoundException;
import com.parkinglot.manager_factory.ParkingManagerFactory;
import com.parkinglot.parking_strategy.ParkingStrategy;
import com.parkinglot.pricing_strategy.PricingStrategy;
import com.parkinglot.repository.ParkingLotRepository;
import com.parkinglot.repository.TicketRepository;
import com.parkinglot.repository.VehicleRepository;
import com.parkinglot.service.ParkingService;
import com.parkinglot.spot_manager.ParkingSpotManager;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingServiceImpl implements ParkingService {

	private final ParkingLotRepository parkingLotRepository;

	private final TicketRepository ticketRepository;

	private final VehicleRepository vehicleRepository;

	@Autowired
	@Qualifier("twoWheelerPricing")
	private PricingStrategy twoWheelerPricing;

	@Autowired
	@Qualifier("fourWheelerPricing")
	private PricingStrategy fourWheelerPricing;

	private final Map<String, ParkingStrategy> parkingStrategies;

	/*// This field will be filled with the current DB state during runtime
	private List<ParkingSpot> spots;
	
	// Get updated list from DB every time
	@Override
	public void refreshSpots() {
		this.spots = parkingLotRepository.findAll();
	}*/

	@Override
	public List<ParkingSpotDTO> getAvailableSpots() {
		List<ParkingSpot> parkingSpots = parkingLotRepository.findByEmptyTrue();

		List<ParkingSpotDTO> parkingSpotDTOs = new ArrayList<>();
		parkingSpots.forEach(parkingSpot -> {
			ParkingSpotDTO dto = new ParkingSpotDTO();
			BeanUtils.copyProperties(parkingSpot, dto);
			dto.setSpotType(parkingSpot.getClass().getSimpleName());
			parkingSpotDTOs.add(dto);
		});
		return parkingSpotDTOs;
	}

	@Override
	@Transactional
	public ParkResponseDTO generateTicket(VehicleRequestDTO dto, String strategyName) throws TicketCreationException {
		// Check if vehicle is already parked
		Optional<Ticket> existingTicket = ticketRepository.findByVehicleVehicleNoAndExitTimeIsNull(dto.getVehicleNo());
		
		if (existingTicket.isPresent()) {
			throw new IllegalStateException("This vehicle is already parked!");
		}
		
		Vehicle v = new Vehicle();
		BeanUtils.copyProperties(dto, v);

		String vehicleNo = v.getVehicleNo();
		// Check if vehicle is already parked
		String id = UUID.randomUUID().toString().concat("-" + vehicleNo);
		v.setVehicleId(id);

		// Resolve vehicle (avoid detached entity exception)
		Vehicle vehicle = vehicleRepository.findById(v.getVehicleId()).orElseGet(() -> vehicleRepository.save(v));

		// Get current available spots from DB
		List<ParkingSpot> spots = parkingLotRepository.findByEmptyTrue();

		// Get strategy
		ParkingStrategy strategy = parkingStrategies.getOrDefault(strategyName,
				parkingStrategies.get("defaultEntrance"));

		// Use strategy to find spot via correct manager
		ParkingSpotManager manager = ParkingManagerFactory.getManager(vehicle.getType(), spots, strategy);
		ParkingSpot spot = manager.findParkingSpot(vehicle);

		if (spot == null) {
			throw new NoParkingSpotAvailableException("No parking spot available for type: " + vehicle.getType());
		}

		// Park vehicle and persist spot update
		spot.parkAvehicle(vehicle);
		parkingLotRepository.save(spot);

		// Create and save ticket
		Ticket ticket = new Ticket();

		ticket.setEntryTime(LocalDateTime.now());
		ticket.setVehicle(vehicle);
		ticket.setSpot(spot);
		ticket = ticketRepository.save(ticket);

		return new ParkResponseDTO(ticket.getId(), ticket.getSpot().getId(), ticket.getVehicle().getVehicleNo(),
				ticket.getVehicle().getType().toString(), ticket.getEntryTime(), "Parking successful");
	}

	@Override
	@Transactional
	public BillResponseDTO removeVehicle(String ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new TicketNotFoundException("Invalid Ticket ID"));

		LocalDateTime now = LocalDateTime.now();
		Duration duration = Duration.between(ticket.getEntryTime(), now);

		PricingStrategy pricingStrategy = ticket.getVehicle().getType() == VehicleType.TWO_WHEELER ? twoWheelerPricing
				: fourWheelerPricing;

		int price = pricingStrategy.calculatePrice(duration);

		// Update parking spot
		ParkingSpot spot = ticket.getSpot();
		spot.removeVehicle();
		parkingLotRepository.save(spot);

		// Store price and exitTime in ticket
		ticket.setExitTime(now);
		ticket.setPrice(price);
		ticket = ticketRepository.save(ticket);

		return new BillResponseDTO(ticket.getId(), ticket.getVehicle().getVehicleNo(),
				ticket.getVehicle().getType().toString(), ticket.getEntryTime(), ticket.getExitTime(),
				duration.toMinutes(), price, "Exit successful. Please pay the amount.");
	}

	@Override
	@Transactional
	public String confirmPayment(String ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
				.orElseThrow(() -> new RuntimeException("Invalid Ticket ID"));

		ticket.setPaid(true);
		return ticketRepository.save(ticket).getId();
	}

	@Override
	public List<SpotStatusDTO> getAllSpotStatus() {
		List<ParkingSpot> spots = parkingLotRepository.findAll();

		return spots.stream().map(spot -> {
			SpotStatusDTO dto = new SpotStatusDTO();
			dto.setSpotId(spot.getId());
			dto.setEmpty(spot.isEmpty());

			// Get last ticket (if any)
			List<Ticket> history = ticketRepository.findBySpot_IdOrderByEntryTimeDesc(spot.getId());
			if (!history.isEmpty()) {
				Ticket last = history.get(0);
				dto.setLastVehicleNo(last.getVehicle().getVehicleNo());
				dto.setLastExitTime(last.getExitTime());
			}
			return dto;
		}).toList();
	}

	@Override
	public List<TicketHistoryDTO> getSpotHistory(Long spotId) {
		List<Ticket> history = ticketRepository.findBySpot_IdOrderByEntryTimeDesc(spotId);
		return history.stream().map(TicketHistoryDTO::new).toList();
	}
}