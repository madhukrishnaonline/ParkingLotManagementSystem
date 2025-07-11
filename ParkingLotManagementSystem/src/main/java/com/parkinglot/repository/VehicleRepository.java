package com.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,String>{

}