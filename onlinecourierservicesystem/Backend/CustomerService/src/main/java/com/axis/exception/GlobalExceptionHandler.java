package com.axis.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//global exception handling
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(IDNotFoundException.class)
	public ResponseEntity<ErrorInfo>  noIdFound(IDNotFoundException exception){
		
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setDateTime(LocalDateTime.now());
		errorInfo.setErrorMessage(exception.getMsg());
		errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
		return new ResponseEntity<ErrorInfo>(errorInfo,HttpStatus.NOT_FOUND);
	}
	


}