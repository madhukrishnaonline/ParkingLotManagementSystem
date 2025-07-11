package com.parkinglot.service;

import java.io.IOException;

import com.lowagie.text.DocumentException;
import com.parkinglot.dto.TicketHistoryDTO;

import jakarta.servlet.http.HttpServletResponse;

public interface TicketService {

	TicketHistoryDTO findTicketById(String ticketId);

	void generateInvoice(String ticketId, HttpServletResponse response) throws IOException, DocumentException;
}