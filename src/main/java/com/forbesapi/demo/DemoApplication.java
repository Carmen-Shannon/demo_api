package com.forbesapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// Setting port to 8080 for our http server and running the application
		System.setProperty("server.port", "8080");
		SpringApplication.run(DemoApplication.class, args);
	}
	
	

}
