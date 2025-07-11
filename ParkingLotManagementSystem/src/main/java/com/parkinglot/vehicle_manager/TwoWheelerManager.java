package com.parkinglot.vehicle_manager;

import java.util.List;

import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.Vehicle;
import com.parkinglot.parking_strategy.ParkingStrategy;
import com.parkinglot.spot_manager.ParkingSpotManager;

public class TwoWheelerManager extends ParkingSpotManager {
	
	private ParkingStrategy strategy;

	public TwoWheelerManager(List<ParkingSpot> spots, ParkingStrategy strategy) {
		super(spots, strategy);
		this.strategy = strategy;
	}

	@Override
	public ParkingSpot findParkingSpot(Vehicle vehicle) {
		return strategy.findParking(spots,vehicle.getType());
	}
}