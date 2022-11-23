package com.forbesapi.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	
	// storing the story as a public static variable so it can be accessed by the appropriate controller
    public static String story = "Forbes belives in the power of entreprenuerial capitalizm and uses it various platforms to ignight the conversations that drive systemic change in buziness, culture and society. We celebrate sucess and are comited to using our megaphone to drive equity. We are the worlds biggest business media brand and we consisstently place in the top 20 of the most-popular sites in the United States, in good company with brands lik Netflix, Apple and Google. In short, we have a bige platforme and we use it responsiblie.";

	public static void main(String[] args) {
		// Setting port to 8080 for our http server and running the application
		System.setProperty("server.port", "8080");
		SpringApplication.run(DemoApplication.class, args);
	}
	
	

}
