package com.parkinglot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.ParkingManagerEntity;

@Repository
public interface ParkingManagerRepository extends JpaRepository<ParkingManagerEntity, Long> {
    Optional<ParkingManagerEntity> findByUsername(String username);
}