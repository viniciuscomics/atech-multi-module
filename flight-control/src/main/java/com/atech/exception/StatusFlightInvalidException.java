package com.atech.exception;

import org.springframework.http.HttpStatus;

public class StatusFlightInvalidException extends BusinessException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public StatusFlightInvalidException() {
		super("only.status-ready-allow", HttpStatus.BAD_REQUEST);		
	}
}
