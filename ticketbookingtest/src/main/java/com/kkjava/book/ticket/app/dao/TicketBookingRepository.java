package com.kkjava.book.ticket.app.dao;

import org.springframework.data.repository.CrudRepository;

import com.kkjava.book.ticket.app.model.Ticket;

public interface TicketBookingRepository extends CrudRepository<Ticket, Integer>{

}
