package com.ticketingSystem.bookingService;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication(exclude = RedisRepositoriesAutoConfiguration.class)
public class BookingServiceApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(BookingServiceApplication.class);
		springApplication.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				System.out.println("=============================================================");
				System.out.println("BOOKING SERVICE STARTED");
				System.out.println("=============================================================");
			}
		});
		springApplication.run(args);
	}

}
