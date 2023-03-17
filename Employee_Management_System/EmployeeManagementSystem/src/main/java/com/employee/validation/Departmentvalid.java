package com.employee.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.employee.model.Department;
import com.employee.repository.DepartmentRepository;



public class Departmentvalid implements Validator{

	@Autowired
	private DepartmentRepository dDao;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		 return Department.class.equals(clazz);
//		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Department d=(Department) target;
	}

	
	

}
