package com.parkinglot.parking_strategy;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.parkinglot.entity.FourWheelerParkingSpot;
import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.TwoWheelerParkingSpot;
import com.parkinglot.entity.VehicleType;

@Component("nearToExit")
public class NearToExit implements ParkingStrategy {

	@Override
	public ParkingSpot findParking(List<ParkingSpot> spots, VehicleType type) {
		return spots.stream().filter(ParkingSpot::isEmpty).filter(spot -> {
			if (type == VehicleType.TWO_WHEELER) {
				return spot instanceof TwoWheelerParkingSpot;
			} else {
				return spot instanceof FourWheelerParkingSpot;
			}
		}).min(Comparator.comparingDouble(ParkingSpot::getPrice)).orElse(null);
	}
}