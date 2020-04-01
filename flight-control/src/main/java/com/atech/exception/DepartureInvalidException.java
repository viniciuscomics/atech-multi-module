package com.atech.exception;

import org.springframework.http.HttpStatus;

public class DepartureInvalidException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DepartureInvalidException() {
		super("departure.invalid", HttpStatus.BAD_REQUEST);		
	}

}
