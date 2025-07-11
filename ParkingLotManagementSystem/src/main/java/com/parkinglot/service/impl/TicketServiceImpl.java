package com.parkinglot.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.parkinglot.dto.TicketHistoryDTO;
import com.parkinglot.entity.Ticket;
import com.parkinglot.exception.TicketNotFoundException;
import com.parkinglot.repository.TicketRepository;
import com.parkinglot.service.TicketService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

	private final TicketRepository ticketRepository;

	@Override
	public TicketHistoryDTO findTicketById(String ticketId) {
		Optional<Ticket> ticket = ticketRepository.findById(ticketId);
		if(ticket.isPresent()) {
			return new TicketHistoryDTO(ticket.get());
		}
		throw new TicketNotFoundException("Ticket Not Found");
	}

	public List<TicketHistoryDTO> getAllTickets() {
		List<Ticket> tickets = ticketRepository.findAll();
		return tickets.stream().map(TicketHistoryDTO::new).toList();
	}

	@Override
	public void generateInvoice(String ticketId, HttpServletResponse response) throws IOException, DocumentException {
		TicketHistoryDTO ticket = findTicketById(ticketId);

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=ticket-invoice.pdf");

		Document document = new Document();
		PdfWriter.getInstance(document, response.getOutputStream());

		document.open();
		document.add(new Paragraph("Parking Ticket Invoice"));
		document.add(new Paragraph("Ticket ID: " + ticket.getTicketId()));
		document.add(new Paragraph("Vehicle No: " + ticket.getVehicleNo()));
		document.add(new Paragraph("Type: " + ticket.getVehicleType()));
		document.add(new Paragraph("Entry Time: " + ticket.getEntryTime()));
		document.add(new Paragraph("Exit Time: " + ticket.getExitTime()));
		document.add(new Paragraph("Price: Rs. " + ticket.getPrice()));
		document.add(new Paragraph("Payment Status: " + (ticket.isPaid() ? "Paid" : "Pending")));
		document.close();
	}
}