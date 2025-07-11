package com.parkinglot.spot_manager;

import java.util.List;

import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.parking_strategy.ParkingStrategy;

public abstract class ParkingSpotManager {

	protected List<ParkingSpot> spots;
	protected ParkingStrategy parkingStrategy;

	protected ParkingSpotManager(List<ParkingSpot> spots, ParkingStrategy strategy) {
		super();
		this.spots = spots;
		this.parkingStrategy = strategy;
	}

	public abstract ParkingSpot findParkingSpot(Vehicle vehicle);

	public void parkVehicle(Vehicle v) {
		ParkingSpot spot = findParkingSpot(v);
		if (spot != null) {
			spot.parkAvehicle(v);
		}
	}

	public void removeVehicle(int spotId) {
		for (ParkingSpot s : spots) {
			if (s.getId() == spotId) {
				s.removeVehicle();
			} else {
				throw new RuntimeException("Spot ID not found: " + spotId);
			}
		}
	}
}