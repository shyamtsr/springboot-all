package com.kkjava.book.ticket.app.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.kkjava.book.ticket.app.dao.TicketBookingRepositoryDao;
import com.kkjava.book.ticket.app.exception.BookingDataAccessException;
import com.kkjava.book.ticket.app.model.Ticket;

/**
 * Author : Shyamsundar T <br>
 * Created on : 06th Aug, 2018 <br>
 * Version : 1.0. <br>
 * Description : Implementation class of TicketBookingRepositoryDao interface. This class
 * is to handle DB calls, required to create and update the ticket booking. <br>
 * JIRA:  <br>
 */
public class TicketBookingRepositoryDaoImpl implements TicketBookingRepositoryDao {
	
	
	 /**
     * LOGGER instance of Logger.
     */
    private static final Logger LOGGER
            = LoggerFactory.getLogger(TicketBookingRepositoryDaoImpl.class);

	
	/**
     * SQL_GET_BOOKING_DETAILS instance of String.
     */
    @Value("${SQL_GET_BOOKING_DETAILS}")
    String SQL_GET_BOOKING_DETAILS;
    
    
    /**
     * userJdbcTemplate instance of JdbcTemplate.
     */
    @Autowired
    @Qualifier("ticketJdbcTemplate")
    JdbcTemplate ticketJdbcTemplate;

    /**
     * namedUserJdbcTemplate instance of NamedParameterJdbcTemplate.
     */
    @Autowired
    @Qualifier("namedTicketJdbcTemplate")
    NamedParameterJdbcTemplate namedTicketJdbcTemplate;

	@Override
	public Ticket insertTicketBooking(Ticket ticket) throws BookingDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
    *
    * @param bomId instance of Integer.
    * @return instance of Ticket.
    * @throws BookingDataAccessException exception.
    */
	@SuppressWarnings("unchecked")
	@Override
	public Ticket fetchTicketBookingInfo(Integer ticketId) throws BookingDataAccessException {
		
	    
		LOGGER.info("*****fetchTicketBookingInfo - STARTS*****");
		Ticket ticketInfo = new Ticket();
        try {
            MapSqlParameterSource paramMap = new MapSqlParameterSource();
            paramMap.addValue("ticketId", ticketId);
            ticketInfo = (Ticket) namedTicketJdbcTemplate.queryForObject(
            		SQL_GET_BOOKING_DETAILS, paramMap,
                    new BeanPropertyRowMapper(Ticket.class));
        } catch (DataAccessException ex) {
            throw new BookingDataAccessException(ex);
        } catch (Exception ex) {
            throw new BookingDataAccessException(ex);
        }
        LOGGER.info("*****fetchTicketBookingInfo - ENDS*****");
        return ticketInfo;
	}

	@Override
	public int existanceBookingCount(Integer ticketId) throws BookingDataAccessException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Ticket> fetchAllBookingInfo() throws BookingDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBookingInfo(Ticket ticketInfo, Integer ticketId) throws BookingDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelBookingDetails(Integer ticketId) throws BookingDataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
