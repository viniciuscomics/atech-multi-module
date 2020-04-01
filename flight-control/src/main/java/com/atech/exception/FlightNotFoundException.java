package com.atech.exception;

import org.springframework.http.HttpStatus;

public class FlightNotFoundException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FlightNotFoundException() {
		super("flight.not-found", HttpStatus.BAD_REQUEST);		
	}

}
