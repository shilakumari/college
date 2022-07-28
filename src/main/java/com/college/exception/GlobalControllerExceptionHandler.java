package com.college.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {
	public static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(WebRequest webRequest, Exception ex) {
		logger.error(ex.getMessage());
		return new ResponseEntity<Object>("{\"error\":\"Internal Server Error\"}", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<Object> handleResponseStatusException(WebRequest webRequest, Exception ex) {
		logger.error(ex.getMessage());
		ResponseStatusException rsException = (ResponseStatusException) ex;
		return new ResponseEntity<Object>("{\"error\":\"" + ex.getMessage() + "\"\"}", rsException.getStatus());
	}

}
