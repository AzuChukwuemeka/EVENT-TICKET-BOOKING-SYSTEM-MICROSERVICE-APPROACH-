package com.ticketingSystem.seatService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SeatServiceApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SeatServiceApplication.class);
		springApplication.setBanner((environment, sourceClass, out) -> {
			out.println("=======================================================================");
			out.println("Seat Service");
			out.println("=======================================================================");
        });
		springApplication.run(args);
	}

}
