package com.kkjava.book.ticket.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kkjava.book.ticket.app.exception.BookingException;
import com.kkjava.book.ticket.app.model.Ticket;
import com.kkjava.book.ticket.app.service.TicketBookingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Author : Shyamsundar T <br>
 * Created on : 06th Aug, 2018 <br>
 * Version : 1.0. <br>
 * Description : Controller class to handle create and update booking requests. <br>
 * JIRA: CM-16053 <br>
 */
@RestController
@RequestMapping(value = "/ticket",
consumes = {MediaType.APPLICATION_JSON_VALUE,
    MediaType.APPLICATION_XML_VALUE},
produces = {MediaType.APPLICATION_JSON_VALUE,
    MediaType.APPLICATION_XML_VALUE})
@Api(value = "ticketBooking", description = "API to Ticket Booking Info.")
public class BookingController {

	final static Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

	@Autowired
	private TicketBookingService bookingService;

	@PostMapping(value = "/booking")
	public ResponseEntity<Ticket> bookingTicket(@RequestBody Ticket ticketInfo) throws Exception {
		if (StringUtils.isBlank(ticketInfo.getEmail())) {
			throw new Exception("Please Enter Email Id..");
		}
		Ticket bookingTicket = bookingService.bookingTicket(ticketInfo);
		return new ResponseEntity<Ticket>(bookingTicket, HttpStatus.CREATED);
	}

	/**
    *
    * @param ticket instance of ticket.
    * @return instance of Ticket.
    * @throws BookingException exception.
    */
   @ApiOperation(value = "Ticket Booking Request",
           response = Ticket.class)
	@PostMapping(value = "/getbookingInfo")
	public Ticket getBookingInfo(@RequestBody final Ticket ticket) throws BookingException {
		Ticket bookingInfo = bookingService.getBookingInfo(ticket.getTicketId());
		return bookingInfo;
	}

	@GetMapping(value = "/getAllbookings")
	public List<Ticket> getAllBookings() throws Exception {

		List<Ticket> bookingList = bookingService.getAllBookings();
		if (CollectionUtils.isEmpty(bookingList)) {
			throw new Exception("There is not Ticket Booking Info, Please Booking..");
		}
		return bookingList;
	}

	@PostMapping(value = "/updatebooking")
	public ResponseEntity<String> updateBookingDetails(@RequestBody final Ticket ticketInfo) {
		String updateBookingDetails = bookingService.updateBookingDetails(ticketInfo);
		return new ResponseEntity<String>(updateBookingDetails, HttpStatus.ACCEPTED);
	}

	@PostMapping(value = "/cancelBooking")
	public ResponseEntity<String> cancelBookings(@RequestBody final Ticket ticketInfo) {
		String cancelBookings = bookingService.cancelBookings(ticketInfo.getTicketId());
		return new ResponseEntity<String>(cancelBookings, HttpStatus.OK);
	}

	@RequestMapping(value = "/uploadfile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		File convert = new File("/Users/shyamsundarreddy/Documents/" + file.getOriginalFilename());

		convert.createNewFile();

		FileOutputStream fileOutputStream = new FileOutputStream(convert);

		fileOutputStream.write(file.getBytes());
		fileOutputStream.close();

		return new ResponseEntity<String>("File is uploaded successfully", HttpStatus.OK);
	}

	@GetMapping(value = "/download/allbookings")
	public ResponseEntity<Object> downloadAllBookings() throws IOException {

		FileWriter fileWriter = null;

		try {

			List<Ticket> allBookings = bookingService.getAllBookings();
			StringBuilder fileContent = new StringBuilder(
					"TicketId, PassengerName, BookingDate, SourceStation, DestinationStation, Email\n");

			allBookings.stream().forEach(bookings -> {
				fileContent.append(bookings.getTicketId()).append(",").append(bookings.getPassengerName()).append(",")
						.append(bookings.getBookingDate()).append(",").append(bookings.getSourceStation()).append(",")
						.append(bookings.getDestinationStation()).append(",").append(bookings.getEmail()).append("\n");
			});

			String filePath = "/Users/shyamsundarreddy/Documents/Bookings/bookings.csv";
			fileWriter = new FileWriter(filePath);
			fileWriter.write(fileContent.toString());
			fileWriter.flush();

			File file = new File(filePath);
			InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.add("Content-Disposition", String.format("attachment:filename=\"%s\"", file.getName()));
			httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
			httpHeaders.add("Pragma", "no-cache");
			httpHeaders.add("Expires", "0");

			ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(httpHeaders)
					.contentLength(file.length()).contentType(MediaType.parseMediaType("application/json"))
					.body(inputStreamResource);

			return responseEntity;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Object>("Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}
	}

}
