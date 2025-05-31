package com.ticketingSystem.ticketServiceConsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketServiceConsumerApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(TicketServiceConsumerApplication.class);
		springApplication.setBanner((environment, sourceClass, out) -> {
            out.println("=====================================================================");
            out.println("PaymentService");
            out.println("=====================================================================");
        });
		springApplication.run(args);
	}

}
