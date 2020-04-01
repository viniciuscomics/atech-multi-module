package com.atech.exception.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.atech.dto.ResponseErrorApi;
import com.atech.exception.BusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ApiExceptionListener extends ResponseEntityExceptionHandler{	
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ResponseErrorApi> handleBusinessException(BusinessException ex, Locale locale){
		log.error("BusinessException", ex);
		String msgUser = messageSource.getMessage(ex.getCode(),null,locale);
		String msgDev = ex.getCause().toString();		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorApi(msgUser,msgDev));		
	}
	
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ResponseErrorApi> handleIllegalArgumentException(IllegalArgumentException ex, Locale locale){		
		log.error("IllegalArgumentException", ex);
		String msgUser = ex.getMessage();
		String msgDev = ex.getCause().toString();		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseErrorApi(msgUser,msgDev));		
	}	
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String msgUser = messageSource.getMessage("message.invalid",null,LocaleContextHolder.getLocale());
		String msgDev = ex.getCause().toString();
		
		return handleExceptionInternal(ex, Arrays.asList(new ResponseErrorApi(msgUser,msgDev)), headers, HttpStatus.BAD_REQUEST, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleExceptionInternal(ex, createListErros(ex.getBindingResult()), headers, HttpStatus.BAD_REQUEST, request);
	 
	}
	
	@ExceptionHandler({DataIntegrityViolationException.class})
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request){
		log.error("DataIntegrityViolationException", ex);
		String msgUser = messageSource.getMessage("resource.operation-not-allow",null,LocaleContextHolder.getLocale());
		String msgDev = ExceptionUtils.getRootCauseMessage(ex);
		
		return handleExceptionInternal(ex, Arrays.asList(new ResponseErrorApi(msgUser,msgDev)),  new HttpHeaders() , HttpStatus.BAD_REQUEST, request);
	}
	
	@ExceptionHandler({EmptyResultDataAccessException.class})	
	public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		log.error("EmptyResultDataAccessException", ex);
		String msgUser = messageSource.getMessage("resource.not-found",null,LocaleContextHolder.getLocale());
		String msgDev = getStringCause(ex);
		return handleExceptionInternal(ex, Arrays.asList(new ResponseErrorApi(msgUser,msgDev)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler({NoSuchElementException.class})	
	public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
		log.error("NoSuchElementException", ex);
		String msgUser = messageSource.getMessage("resource.not-found",null,LocaleContextHolder.getLocale());
		String msgDev = getStringCause(ex);
		return handleExceptionInternal(ex, Arrays.asList(new ResponseErrorApi(msgUser,msgDev)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	private String getStringCause(RuntimeException ex) {		
		return ex.getCause() == null?ex.toString():ex.getCause().toString();
	}
	
	private List<ResponseErrorApi> createListErros(BindingResult binding){
		List<ResponseErrorApi> errors = new ArrayList<>();
		
		for(FieldError field : binding.getFieldErrors()) {
			String msgUser  = messageSource.getMessage(field, LocaleContextHolder.getLocale());			
			errors.add(new ResponseErrorApi(msgUser,field.toString()));	
		}		
		
		return errors;
	}
	
}
