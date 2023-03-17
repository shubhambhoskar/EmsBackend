package com.employee.exception;

import java.time.LocalDateTime;

import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public ResponseEntity<MyErrorDetails> employeeExceptionHandler(EmployeeNotFoundException e, WebRequest req){
		
		MyErrorDetails me = new MyErrorDetails();
		me.setTimeStamp(LocalDateTime.now());
		me.setMessage(e.getMessage());
		me.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(me, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DepartmentNotFoundException.class)
	public ResponseEntity<MyErrorDetails> departmentExceptionHandler(DepartmentNotFoundException e, WebRequest req){
		
		MyErrorDetails me = new MyErrorDetails();
		me.setTimeStamp(LocalDateTime.now());
		me.setMessage(e.getMessage());
		me.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(me, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidException.class)
	public ResponseEntity<MyErrorDetails> invalidExceptionHandler(InvalidException e, WebRequest req){
		MyErrorDetails me = new MyErrorDetails();
		me.setTimeStamp(LocalDateTime.now());
		me.setMessage(e.getMessage());
		me.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(me, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<MyErrorDetails> exceptionHandler(Exception e, WebRequest req){
		MyErrorDetails me = new MyErrorDetails();
		me.setTimeStamp(LocalDateTime.now());
		me.setMessage(e.getMessage());
		me.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(me, HttpStatus.BAD_REQUEST);
	}
	
	

	@ExceptionHandler(NotReadablePropertyException.class)
	public ResponseEntity<MyErrorDetails> exceptionHandler(NotReadablePropertyException e, WebRequest req){
		MyErrorDetails me = new MyErrorDetails();
		me.setTimeStamp(LocalDateTime.now());
		me.setMessage(e.getMessage());
		me.setDetails(req.getDescription(false));
		
		return new ResponseEntity<MyErrorDetails>(me, HttpStatus.BAD_REQUEST);
	}
	
}
