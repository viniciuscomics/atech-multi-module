package com.atech.exception.listener;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.atech.dto.ResponseErrorApi;

import lombok.extern.slf4j.Slf4j;

@Order(Ordered.LOWEST_PRECEDENCE)
@RestControllerAdvice
@Slf4j
public class GeneralExceptionListener {
		
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseErrorApi> handleInternalServerError(Exception ex, Locale locale){
		
		log.error("Aconteceu um erro inesperado", ex);
		String msgUser = messageSource.getMessage("generic.error",null,locale);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseErrorApi(msgUser, ex.getMessage()));
	}
	
}
