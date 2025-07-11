package com.parkinglot.exception;

public class TicketCreationException extends RuntimeException {
	
	private static final long serialVersionUID = -5119657208928192607L;

	public TicketCreationException(String message) {
		super(message);
	}
}