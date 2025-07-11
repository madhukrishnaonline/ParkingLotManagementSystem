package com.parkinglot.exception;

public class NoParkingSpotAvailableException extends RuntimeException {

	private static final long serialVersionUID = -8661275019113250257L;

	public NoParkingSpotAvailableException(String message) {
		super(message);
	}
}