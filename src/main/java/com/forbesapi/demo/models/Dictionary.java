package com.forbesapi.demo.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="words")
public class Dictionary {
	// setting up a constructor
	public Dictionary(String word) {
		this.word = word;
	}
	
	// constructor overload 
	public Dictionary() {
		
	}
	
	// this model represents our dictionary of words as a MySQL table, it assigns a unique incrementing id to each word
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String word;
	
	// setting created at and updated at columns within the words table to automatically set the date upon creation
	// and update the date upon editing a word
	@Column(updatable=false)
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date createdAt;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date updatedAt;
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
		this.updatedAt = new Date();
	}
	
	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	// getters and setters
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long newId) {
		this.id = newId;
	}
	
	public String getWord() {
		return this.word;
	}
	
	public void setWord(String newWord) {
		this.word = newWord;
	}
	
	public Date getCreatedAt() {
		return this.createdAt;
	}
	
	public Date getUpdatedAt() {
		return this.updatedAt;
	}
	
	public void setUpdatedAt() {
		this.updatedAt = new Date();
	}
}
