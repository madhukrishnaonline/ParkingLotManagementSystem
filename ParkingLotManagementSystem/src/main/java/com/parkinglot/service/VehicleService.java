package com.parkinglot.service;

import java.util.List;

import com.parkinglot.dto.VehicleHistoryDTO;

public interface VehicleService {

	List<VehicleHistoryDTO> getVehicleHistory(String vehicleNo);

}