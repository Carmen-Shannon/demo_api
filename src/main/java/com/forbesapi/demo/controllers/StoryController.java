package com.forbesapi.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.forbesapi.demo.services.StoryService;

// setting up the story controller will not require additional repositories or models as it's an immutable string that 
// compares itself to entries in the database, it does require a service to store the algorithm for finding close words
@RestController
public class StoryController {
	
	// dependency injection
	@Autowired
	private StoryService ss;
	
	public StoryController(StoryService ss) {
		this.ss = ss;
	}
	
	// sole route for story, on post it will deliver a 2d array that holds the word with it's closest match
	@SuppressWarnings("rawtypes")
	@PostMapping("/story")
	public ResponseEntity story() {
		
		try {
			return new ResponseEntity<ArrayList<ArrayList<String>>>(ss.findMatches(), HttpStatus.OK);
		} catch (BadRequest e) {
			return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}
	
}
