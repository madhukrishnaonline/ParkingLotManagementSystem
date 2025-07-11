package com.parkinglot.controllers;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.parkinglot.dto.TicketHistoryDTO;
import com.parkinglot.service.TicketService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketHistoryController {

	private final TicketService service;

	@GetMapping("/history/{ticketId}")
	public ResponseEntity<TicketHistoryDTO> getTicketDetails(@PathVariable String ticketId) {
		TicketHistoryDTO ticket = service.findTicketById(ticketId);
		return ticket == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(ticket);
	}

	@GetMapping("/invoice/{ticketId}")
	public ResponseEntity<Void> generateInvoice(@PathVariable String ticketId, HttpServletResponse response)
			throws IOException {
		try {
			service.generateInvoice(ticketId, response);
			return ResponseEntity.ok().build();
		} catch (DocumentException e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}
	}
}
/*@GetMapping("/invoice/pdf/{ticketId}")
public ResponseEntity<byte[]> downloadInvoicePdf(@PathVariable String ticketId) {
    byte[] pdf = invoiceService.generatePdf(ticketId);
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + ticketId + ".pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
}*/