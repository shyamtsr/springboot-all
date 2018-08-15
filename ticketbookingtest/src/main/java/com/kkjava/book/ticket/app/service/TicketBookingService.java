package com.kkjava.book.ticket.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kkjava.book.ticket.app.dao.TicketBookingRepository;
import com.kkjava.book.ticket.app.dao.TicketBookingRepositoryDao;
import com.kkjava.book.ticket.app.exception.BookingException;
import com.kkjava.book.ticket.app.model.Ticket;

@Service
public class TicketBookingService {

	@Autowired
	private TicketBookingRepository bookingRepository;
	
	@Autowired
	private TicketBookingRepositoryDao ticketBookingRepositoryDao;

	public Ticket bookingTicket(Ticket ticketInfo) {
		Ticket ticket = new Ticket();
		if (ticketInfo != null) {
			ticket = bookingRepository.save(ticketInfo);
		}
		return ticket;
	}

	/*public Ticket getBookingInfo(int ticketId) {
		Ticket ticketInfo = new Ticket();
		if (ticketId != 0) {
			ticketInfo = bookingRepository.findOne(ticketId);
		}

		return ticketInfo;
	}*/
	
	public Ticket getBookingInfo(int ticketId) throws BookingException {
		Ticket ticketInfo = new Ticket();
		if (ticketId != 0) {
			ticketInfo = ticketBookingRepositoryDao.fetchTicketBookingInfo(ticketId);
		}

		return ticketInfo;
	}
	
	

	public List<Ticket> getAllBookings() {

		List<Ticket> allBookingInfo = (List<Ticket>) bookingRepository.findAll();

		return allBookingInfo;
	}

	public String updateBookingDetails(final Ticket ticketInfo) {

		String response = "";
		Integer ticketId = 2;//getBookingInfo(ticketInfo.getTicketId()).getTicketId();

		// Integer ticketId =
		// bookingRepository.findOne(ticketInfo.getTicketId()).getTicketId();

		Ticket ticketDetails = new Ticket();
		if (ticketId == ticketInfo.getTicketId()) {
			ticketDetails = bookingRepository.save(ticketInfo);
			response = "Ticket Booking " + ticketDetails.getTicketId() + " is Updated.";
		} else {
			response = "Invalid Ticket Id : " + ticketId + ", Please Enter Valid Booking Info";
		}
		return response;
	}

	public String cancelBookings(Integer bookingId) {

		String response = "";
		Integer ticketId = 2;//getBookingInfo(bookingId).getTicketId();
		if (ticketId == bookingId) {
			bookingRepository.delete(bookingId);
			response = "Cancel Ticket Booking " + ticketId + ".";
		} else {
			response = "Invalid Ticket Id : " + ticketId + ", Please Enter Valid Booking Info";
		}
		return response;
	}

}
