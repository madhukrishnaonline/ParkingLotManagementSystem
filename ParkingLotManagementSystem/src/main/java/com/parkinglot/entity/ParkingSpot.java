package com.parkinglot.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Parking_Spot")
@DiscriminatorColumn(name = "spot_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ParkingSpot {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private boolean empty = true;

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Vehicle vehicle;

	private double price;

	public boolean isEmpty() {
		return this.empty;
	}

	public void parkAvehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.empty = false;
	}

	public void removeVehicle() {
		this.vehicle = null;
		this.empty = true;
	}
}