package com.employee.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.MyErrorDetails;
import com.employee.model.Employee;
import com.employee.model.EmployeeDto;
import com.employee.model.EmployeePage;
import com.employee.model.EmployeeSearchCriteria;
import com.employee.model.ErrorResponse;
import com.employee.model.ResponseDTO;
import com.employee.service.CriteriaEmployeeService;
import com.employee.service.EmployeeService;
import com.employee.validation.EmployeeValidator;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EmployeeController {
	
	@Autowired
	private EmployeeService sService;
	
	@Autowired
	private EmployeeValidator validator;
	
	@Autowired
	private CriteriaEmployeeService cService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	//done
	@PostMapping("/Employee")
	public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeDto employee, Errors errors){
		
//		if(errors.hasErrors()) {
//			return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
//		}
//		
//		String result = sService.registerEmployee(employee);
//		
//		return new ResponseEntity<>(result, HttpStatus.OK);
		
		
		 if(errors.hasErrors()){
	            return new ResponseEntity<>(ResponseDTO.getResponse(
	                    errors, LocalDateTime.now(),HttpStatus.BAD_REQUEST,HttpStatus.BAD_REQUEST.value(), ErrorResponse.getErrorResponse(errors)), HttpStatus.BAD_REQUEST);
	        }
		 	String result=sService.registerEmployee(employee);
	        return new ResponseEntity<>(ResponseDTO.getResponse(result,LocalDateTime.now()
	                ,HttpStatus.OK,HttpStatus.OK.value(),null),HttpStatus.OK);
	}
	
	//done
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeEmployee(@PathVariable ("id") Integer id) throws EmployeeNotFoundException{
		
		String result = sService.deleteEmployee(id);
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	
	//done
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable ("id") Integer id, @Valid @RequestBody EmployeeDto employee, Errors errors) throws EmployeeNotFoundException{
		
		if(errors.hasErrors()) {
			return new ResponseEntity<>(errors.getAllErrors(), HttpStatus.BAD_REQUEST);
		}
		
		String result = sService.updateEmployee(id, employee);
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
	//done
	@GetMapping("id/{id}")
	public ResponseEntity<Employee> getEmployeeByID(@PathVariable("id") Integer id) throws EmployeeNotFoundException{
		
		Employee result = sService.getEmployeeByID(id);
		
		return new ResponseEntity<Employee>(result, HttpStatus.OK);
	}
	//done
	@GetMapping("/Employees")
	public ResponseEntity<List<Employee>> getAllEmployees(){
		
		List<Employee> result = sService.getAllEmployeesList();
		
		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/department/{id}")
	public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable("id") Integer id) throws DepartmentNotFoundException{
		
		List<Employee> result = sService.getEmployeeByDepartment(id);
		
		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);
	}
	
	@GetMapping("/paginationAndSort/{offset}/{pageSize}/{field}")
	public ResponseEntity<Page<Employee>> getEmployeesWithPaginationAndSorting(@PathVariable("offset") Integer offset, @PathVariable("pageSize") Integer pageSize, @PathVariable("field") String field){
		
		Page<Employee> result = sService.findEmployeeWithPaginationAndSorting(offset, pageSize, field);
		
		return new ResponseEntity<Page<Employee>>(result, HttpStatus.ACCEPTED);
		
	}
	
	//SORTING
	@GetMapping("/sortEmployees/{field}")
	ResponseEntity<List<Employee>> listofempsortingHandler(@PathVariable String field) throws EmployeeNotFoundException{
			
		List<Employee>l=sService.findEmployeewithsorting(field);
			
		return new ResponseEntity<List<Employee>>(l,HttpStatus.OK);
	}
		
	//PAGINATION
	@GetMapping("/paginationEmployees/{offset}/{pageSize}")
	ResponseEntity<List<Employee>> paginationHandler(@PathVariable int offset,@PathVariable int pageSize){
		
//		Page<Employee>p=sService.getemployeebypagination(offset, pageSize);
		List<Employee>p=sService.getemployeebypagination(offset, pageSize).getContent();	
		return new ResponseEntity<List<Employee>>(p,HttpStatus.OK);
	}
	
	@GetMapping("/searching/{s}")
	ResponseEntity<List<Employee>> searchHandler(@PathVariable String s) throws EmployeeNotFoundException {
		
		List<Employee>l=sService.searchAllbyfirstname(s);
		
		return new ResponseEntity<List<Employee>>(l,HttpStatus.OK);
	}
	
	
	
	// criteria Query
	@GetMapping("/pagination/{pageNumber}/{pageSize}/{searchingfield}/{sortBy}/{direction}")
	public ResponseEntity<Page<Employee>> getEmployees(@PathVariable int pageNumber,
														@PathVariable int pageSize,
														@PathVariable String searchingfield,
														@PathVariable String sortBy,
														@PathVariable Boolean direction){
		
		
		EmployeePage employeePage= new EmployeePage();
	
		EmployeeSearchCriteria ecriteria= new EmployeeSearchCriteria();
		
		employeePage.setPageNumber(pageNumber);
		employeePage.setPageSize(pageSize);
		
		if(direction ==true) employeePage.setSortDirection(Sort.Direction.ASC);
		else employeePage.setSortDirection(Sort.Direction.DESC);
	
		employeePage.setSortBy(sortBy);
		if(searchingfield.equals("string")==false) {
			
			ecriteria.setEmployeeFirstName(searchingfield);
			ecriteria.setEmployeeLastName(searchingfield);
		}
		
		return new ResponseEntity<>(cService.getEmployees(employeePage, ecriteria),HttpStatus.OK);
	}

}
