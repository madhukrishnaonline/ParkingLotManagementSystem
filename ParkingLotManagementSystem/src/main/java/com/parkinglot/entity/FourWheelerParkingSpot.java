package com.parkinglot.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("FOUR_WHEELER")
public class FourWheelerParkingSpot extends ParkingSpot {
    public FourWheelerParkingSpot() {
        super(null, true, null,40); // price = 40
    }
}