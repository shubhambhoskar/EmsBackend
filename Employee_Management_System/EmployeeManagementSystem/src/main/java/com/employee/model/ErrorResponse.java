package com.employee.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.validation.Errors;

public class ErrorResponse {

	 public static List<Map<String,String>> getErrorResponse(Errors errors){
	        
		 List<Map<String,String>> list= new ArrayList<>();

	        errors.getFieldErrors().forEach(e->{
	            Map<String,String> map= new HashMap<>();
	            map.put("Field", e.getField());
	            map.put("Message",e.getDefaultMessage());
	            map.put("Error Code", e.getCode());
	            list.add(map);

	        });
	        return list;
	    }
}
