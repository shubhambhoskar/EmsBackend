package com.employee.validation;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.el.util.Validation;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.employee.controller.EmployeeController;
import com.employee.model.Employee;
import com.employee.model.EmployeeDto;

@ControllerAdvice(assignableTypes = EmployeeController.class)
public class EmployeeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return EmployeeDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
        
		EmployeeDto employee = (EmployeeDto) target;
		
		if(employee.getEmployeeFirstName() == null ) {
			errors.rejectValue("FirstName", "It is null ", "firstName should not be null");
			
			throw new IllegalArgumentException("firstName should not be null");
		}
		if(!Character.isUpperCase(employee.getEmployeeFirstName().charAt(0))) {
			errors.rejectValue("FirstName", "try again", "First Character of firstname should be Capital");
		}
		
		if(employee.getEmployeeLastName() == null ) {
			errors.rejectValue("LastName", "It is null", "lastName should not be null");
		}
		
		if(!Character.isUpperCase(employee.getEmployeeLastName().charAt(0))) {
			errors.rejectValue("LastName", "try again", "First Character should be Capital");
		}
		
		if(employee.getEmailId() == null ) {
			errors.rejectValue("emailId", "Email should not be null", "Email Address should not be null");
		} 
		
		Pattern pattern1 = Pattern.compile("^.+@.+\\..+$");
		Matcher matcher1 = pattern1.matcher(employee.getEmailId());
		
		if(!matcher1.matches()) {
			errors.rejectValue("emailId", "Invalid Email", "Enter a valid Email Address");
		}
		
		if(employee.getPhoneNumber() == null ) {
			errors.rejectValue("phoneNumber", "Phone Number should not be null", "Enter your Phone Number");
		}
		
		if(employee.getPhoneNumber().length() != 10) {
			errors.rejectValue("phoneNumber", "Enter valid Phone Number", "Phone Number length should be 10");
		}
		
		Pattern pattern2 = Pattern.compile("[0-9]+");
		Matcher matcher2 = pattern2.matcher(employee.getPhoneNumber());
		if(!matcher2.matches()) {
			errors.rejectValue("phoneNumber", "Enter valid Phone Number", "Phone Number should contain only numbers");
		}

	}

}
