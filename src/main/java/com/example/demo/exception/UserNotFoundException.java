package com.example.demo.exception;

public class UserNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(Long id) {
		super("Utilisateur introuvable avec : "+id);
	}
	
	public UserNotFoundException(String nom) {
		super("Utilisateur introuvable avec le nom : "+nom);
	}
}
