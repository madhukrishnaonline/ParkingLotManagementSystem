package com.parkinglot.exception;

public class ManagerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7969548658805155707L;

	public ManagerNotFoundException(String msg) {
		super(msg);
	}
}