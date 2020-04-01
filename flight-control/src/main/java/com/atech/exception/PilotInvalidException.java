package com.atech.exception;

import org.springframework.http.HttpStatus;

public class PilotInvalidException extends BusinessException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PilotInvalidException() {
		super("pilot.busy", HttpStatus.BAD_REQUEST);		
	}

}
