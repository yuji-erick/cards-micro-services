package com.eys.cardsms.exceptions;

public class DuplicatedCardException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2925608052776834018L;
	
	public DuplicatedCardException() {}
	
	public DuplicatedCardException(String message) {
		super(message);
	}
	
}
