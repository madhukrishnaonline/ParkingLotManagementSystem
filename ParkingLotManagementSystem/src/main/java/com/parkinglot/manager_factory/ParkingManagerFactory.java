package com.parkinglot.manager_factory;

import java.util.List;

import org.springframework.stereotype.Component;

import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.VehicleType;
import com.parkinglot.parking_strategy.ParkingStrategy;
import com.parkinglot.spot_manager.ParkingSpotManager;
import com.parkinglot.vehicle_manager.FourWheelerManager;
import com.parkinglot.vehicle_manager.TwoWheelerManager;

@Component
public class ParkingManagerFactory {

	public static ParkingSpotManager getManager(VehicleType type, List<ParkingSpot> spots, ParkingStrategy strategy) {
		switch (type) {
		case TWO_WHEELER -> {
			return new TwoWheelerManager(spots, strategy);
		}
		case FOUR_WHEELER -> {
			return new FourWheelerManager(spots, strategy);
		}
		default -> throw new IllegalArgumentException("Unsupported vehicle type: " + type);
		}
	}

	private ParkingManagerFactory() {
	}
}