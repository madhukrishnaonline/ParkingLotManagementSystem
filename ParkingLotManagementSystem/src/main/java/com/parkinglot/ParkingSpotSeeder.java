package com.parkinglot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parkinglot.entity.FourWheelerParkingSpot;
import com.parkinglot.entity.TwoWheelerParkingSpot;
import com.parkinglot.repository.ParkingLotRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ParkingSpotSeeder implements CommandLineRunner {

	private final ParkingLotRepository spotRepository;

	@Override
	public void run(String... args) {
		if (spotRepository.count() == 0) {
			// Add 5 two-wheeler and 5 four-wheeler spots
			for (int i = 0; i < 5; i++) {
				spotRepository.save(new TwoWheelerParkingSpot());
				spotRepository.save(new FourWheelerParkingSpot());
			}
			System.out.println("Parking spots initialized!");
		}
	}
}