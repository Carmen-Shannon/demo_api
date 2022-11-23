package com.forbesapi.demo.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoryService {
	
	//dependency injecting the dictionary service so we can collect all of the words in the dictionary
	@Autowired
	private DictionaryService ds;
	
	public StoryService(DictionaryService ds) {
		this.ds = ds;
	}
	
	// saving the story as a private static string as it only needs to be accessed within this service
	private static String Story = "Forbes belives in the power of entreprenuerial capitalizm and uses it various platforms to ignight the conversations that drive systemic change in buziness, culture and society. We celebrate sucess and are comited to using our megaphone to drive equity. We are the worlds biggest business media brand and we consisstently place in the top 20 of the most-popular sites in the United States, in good company with brands lik Netflix, Apple and Google. In short, we have a bige platforme and we use it responsiblie.";
	
	// defining a static variable Alpha that contains the alphabet - using this to check the last letter of each word
	private static String Alpha = "abcdefghijklmnopqrstuvwxyz";
	
	// setting up a simple function to clean the given string
	// specifically remove punctuation at the end of words, it returns a string in all lowercase with no punctuation
	private static String cleanString() {

		// cleanedString is what we will be returning
		String cleanedString = "";
		String[] splitString = Story.split(" ");
		
		// loop through the character array made by splitting all words by whitespace
		for (int i=0;i<splitString.length;i++) {
			// kind of verbose line but just checking the last character against the Alpha variable, if it's not a letter then 
			// remove it, otherwise add the lowercase version of the current word to the final string
			if (Alpha.indexOf(splitString[i].charAt(splitString[i].length()-1)) == -1) {
				cleanedString += splitString[i].toLowerCase().substring(0, splitString[i].length()-1);
			} else {
				cleanedString += splitString[i].toLowerCase();
			}
			
			// since we return a string, adding whitespace between words to make it easier to split
			if (i < splitString.length) {
				cleanedString += " ";
			}
		}
		
		return cleanedString;
		
	}
	
	// checking each word in the story to see if it has a match in the database, if not it will add the word and it's closest match to this
	// 2d array to be returned
	public ArrayList<ArrayList<String>> findMatches() {
		
		String[] storyArr = cleanString().split(" ");
		
		ArrayList<ArrayList<String>> finalArrayList = new ArrayList<ArrayList<String>>();
		ArrayList<String> Dictionary = ds.allWords();
		
		// some nested loops, first will check each word in the story:
		// if the word does not exist in the database then loop through each word in the database to check for a close match
		for (String s : storyArr) {
			if (ds.findWord(s.toLowerCase()) == null) {
				
				// initializing variables and adding our word from the story to our inner array
				ArrayList<String> wordAndMatch = new ArrayList<String>(2);
				String closestMatch = "";
				int highestMatchScore = 0;
				wordAndMatch.add(s);
				
				// looping through each word in the dictionary and generating a score based on how similar it is
				for (String d : Dictionary) {
					int i = 0;
					int matchScore = 0;
					while (i<d.length() && i<s.length()) {
						if (d.charAt(i) == s.charAt(i)) {
							matchScore+=1;
						}
						i+=1;
					}
					
					// if the score is higher than the current highest score, replace the current closest match with new word
					if (matchScore > highestMatchScore) {
						highestMatchScore = matchScore;
						closestMatch = d;
					}
				}
				// finally push the closest match into the inner array and push the inner array into the outer array
				wordAndMatch.add(closestMatch);
				finalArrayList.add(wordAndMatch);
			}
		}
		
		return finalArrayList;
		
	}

}
