package com.employee.validation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME) 
@Documented
@Constraint(validatedBy = DepartmentTypeValidator.class)
public @interface DepartmentNames {

	public String message() default "Invalid Department . It should be java or angular";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

	
}
