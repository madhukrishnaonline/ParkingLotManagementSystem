package com.parkinglot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.ParkingSpot;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingSpot,Integer>{
	List<ParkingSpot> findByEmptyTrue();
}