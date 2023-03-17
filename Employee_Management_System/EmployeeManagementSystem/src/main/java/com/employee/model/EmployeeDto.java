package com.employee.model;

import javax.persistence.Column;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class EmployeeDto {

	private String employeeFirstName;
	
	private String employeeLastName;
	
	private String emailId;

	private String phoneNumber;
	
	
}
