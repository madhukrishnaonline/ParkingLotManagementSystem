package com.parkinglot.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TWO_WHEELER")
public class TwoWheelerParkingSpot extends ParkingSpot {
	public TwoWheelerParkingSpot() {
		super(null, true, null, 20); // ID=null, empty=true, price=20
	}
}