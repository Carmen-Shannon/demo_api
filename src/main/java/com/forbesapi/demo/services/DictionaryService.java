package com.forbesapi.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forbesapi.demo.models.Dictionary;
import com.forbesapi.demo.repositories.DictionaryRepo;

@Service
public class DictionaryService {
	
	@Autowired
	private DictionaryRepo dr;
	
	// setting up dependency injection to use the repo within the service
	public DictionaryService(DictionaryRepo dr) {
		this.dr = dr;
	}
	
	// create a new word in the dictionary
	public void create(String newWord) {
		dr.save(new Dictionary(newWord.toLowerCase()));
	}
	
	// fetch all words
	public ArrayList<String> allWords() {
		// converting an arrayList full of dictionary objects to an arraylist full of just strings that are the words only
		ArrayList<Dictionary> beforeConversion = (ArrayList<Dictionary>) dr.findAll();
		ArrayList<String> returnList = new ArrayList<String>();
		
		for (int i = 0; i < beforeConversion.size(); i += 1) {
			returnList.add(beforeConversion.get(i).getWord());
		}
		
		return returnList;
	} 
	
	// find word in dictionary
	public String findWord(String search) {
		Optional<Dictionary> word = dr.findByWord(search);
		
		// checking the optional to see if the word exists in the database, if it does return the string of just the word, otherwise return null
		return word.isPresent() ? word.get().getWord() : null;
	}
	
	// delete word in dictionary
	public void deleteWord(String query) {
		
		Optional<Dictionary> foundWord = dr.findByWord(query);
		
		if (foundWord.isPresent()) {
			dr.delete(foundWord.get());
		}
	}

}
