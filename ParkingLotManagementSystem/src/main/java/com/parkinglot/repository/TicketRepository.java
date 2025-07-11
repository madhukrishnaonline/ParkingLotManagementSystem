package com.parkinglot.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.parkinglot.entity.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, String> {

	List<Ticket> findByVehicle_VehicleNoOrderByEntryTimeDesc(String vehicleNo);

	/*@Query("SELECT t FROM Ticket t JOIN FETCH t.vehicle JOIN FETCH t.spot WHERE t.vehicle.vehicleNo = :vehicleNo")
	List<Ticket> findFullByVehicleNo(@Param("vehicleNo") String vehicleNo);*/

	List<Ticket> findBySpot_IdOrderByEntryTimeDesc(Long id);
	
	Optional<Ticket> findByVehicleVehicleNoAndExitTimeIsNull(String vehicleNo);
}