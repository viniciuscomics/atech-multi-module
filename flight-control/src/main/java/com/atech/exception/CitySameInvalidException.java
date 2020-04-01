package com.atech.exception;

import org.springframework.http.HttpStatus;

public class CitySameInvalidException extends BusinessException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CitySameInvalidException() {
		super("same.city-not-allow", HttpStatus.BAD_REQUEST);		
	}

}
