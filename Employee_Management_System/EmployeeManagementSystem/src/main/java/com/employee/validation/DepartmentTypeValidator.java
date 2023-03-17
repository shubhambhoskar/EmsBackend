package com.employee.validation;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DepartmentTypeValidator implements ConstraintValidator<DepartmentNames, String>{

	@Override
	public boolean isValid(String deptype, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		
		List<String> dept=Arrays.asList("java","angular");
		
		return dept.contains(deptype);
		
	}

}
