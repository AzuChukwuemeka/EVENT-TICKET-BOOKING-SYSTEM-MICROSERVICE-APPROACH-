package com.ticketingSystem.seatServiceConsumer;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication()
public class SeatServiceConsumerApplication {
	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SeatServiceConsumerApplication.class);
		springApplication.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				out.println("=================================================");
				out.println("EVENT SEAT GENERATION SERVICE");
				out.println("=================================================");
			}
		});
		springApplication.run(args);
	}
}
