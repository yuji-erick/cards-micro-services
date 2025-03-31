package com.eys.cardsms.exceptions;

public class InvalidCardException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3014110580484081842L;

	public InvalidCardException() {}
	
	public InvalidCardException(String message) {
		super(message);
	}

}
