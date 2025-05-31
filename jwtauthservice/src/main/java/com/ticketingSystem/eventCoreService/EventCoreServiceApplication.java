package com.ticketingSystem.eventCoreService;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class EventCoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(EventCoreServiceApplication.class);
		springApplication.setBanner(new Banner() {
			@Override
			public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
				out.println("========================================");
				out.println("JWT AUTH SERVICE");
				out.println("========================================");
			}
		});
		springApplication.run();
	}

}
