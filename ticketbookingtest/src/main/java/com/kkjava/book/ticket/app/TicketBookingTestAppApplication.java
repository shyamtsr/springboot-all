package com.kkjava.book.ticket.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
// @EnableDiscoveryClient
public class TicketBookingTestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketBookingTestAppApplication.class, args);
	}
}
