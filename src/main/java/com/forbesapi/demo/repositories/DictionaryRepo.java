package com.forbesapi.demo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.forbesapi.demo.models.Dictionary;

public interface DictionaryRepo extends CrudRepository<Dictionary, Long> {
	
	// the repo will serve as an interface to return an Optional in case the word does not exist in the dictionary
	Optional<Dictionary> findByWord(String word);
	
	// simple find all method to return all words in the dictionary
	ArrayList<Dictionary> findAll();

}
