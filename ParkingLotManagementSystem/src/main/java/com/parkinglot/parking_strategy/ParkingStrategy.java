package com.parkinglot.parking_strategy;

import java.util.List;

import com.parkinglot.entity.ParkingSpot;
import com.parkinglot.entity.VehicleType;

public interface ParkingStrategy {

	ParkingSpot findParking(List<ParkingSpot> spots,VehicleType type);
}