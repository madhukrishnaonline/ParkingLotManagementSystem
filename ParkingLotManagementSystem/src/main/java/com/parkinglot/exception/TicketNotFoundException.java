package com.parkinglot.exception;

public class TicketNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(String ticketId) {
		super("Ticket with ID '" + ticketId + "' not found.");
	}
}