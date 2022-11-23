package com.forbesapi.demo.controllers;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.forbesapi.demo.services.DictionaryService;

@RestController
public class DictionaryController {
	
	// dependency injection for service to be used within controller
	@Autowired
	private DictionaryService ds;
	
	public DictionaryController(DictionaryService ds) {
		this.ds = ds;
	}
	
	// not requested in instructions of the project but setting up a route to catch when a user might enter an invalid path so there are no
	// inconsistencies, otherwise spring boot sends its own 404 response in a different format
	@RequestMapping(value= {"/*", "/dictionary/*"})
	public ResponseEntity<String> catchInvalidPath() {
		return new ResponseEntity<String>("Path not found", HttpStatus.NOT_FOUND);
	}
	
	
	// main path to return all words within the dictionary
	@SuppressWarnings("rawtypes")
	@GetMapping("/dictionary")
	public ResponseEntity fetchAllDictionary() {
		// setting up an arraylist of dictionary objects to be returned within the responseEntity, catches an internal server error
		try {
			ArrayList<String> dictArray = ds.allWords();
			return new ResponseEntity<ArrayList<String>>(dictArray, HttpStatus.OK);
		} catch (InternalServerError e) {
			return new ResponseEntity<String>("Unable to retrieve data", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// adding entries to the dictionary via path variables, it will convert the url variable to a string before 
	// checking against current entries in the dictionary and returning the appropriate response
	@SuppressWarnings("rawtypes")
	@PostMapping("/dictionary/add/{newWord}")
	public ResponseEntity addDictionaryEntry(@PathVariable String newWord) {
		try {
			String foundWord = ds.findWord(newWord);
			if (foundWord != null) {
				return new ResponseEntity<String>("Duplicate entry", HttpStatus.OK);
			}
			// adding word to the database before returning response
			ds.create(newWord);
			return new ResponseEntity(HttpStatus.ACCEPTED);
			
		} catch (BadRequest e) {
			return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// removing entries from the dictionary, first checking if the word exists within the database
	// then returning the appropriate response
	@SuppressWarnings("rawtypes")
	@PostMapping("/dictionary/delete/{query}")
	public ResponseEntity deleteDictionaryEntry(@PathVariable String query) {
		try {
			String foundWord = ds.findWord(query);
			if (foundWord != null) {
				// if the word exists then remove it from the database before returning response
				ds.deleteWord(foundWord);
				return new ResponseEntity(HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<String>("Entry not found", HttpStatus.NOT_FOUND);
			
		} catch (BadRequest e) {
			return new ResponseEntity<String>("Bad request", HttpStatus.BAD_REQUEST);
		}
	}
	
}
