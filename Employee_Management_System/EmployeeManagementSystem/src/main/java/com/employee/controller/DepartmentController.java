package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.model.EmployeeDto;
import com.employee.service.DepartmentService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/department")
public class DepartmentController {
	
	@Autowired
	private DepartmentService departmentService;

	//done
	@PostMapping("/register")
	public ResponseEntity<String> addDepartment(@RequestBody Department department){
		String result = departmentService.registerDepartment(department);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@GetMapping("/empd/{dept}")
	public ResponseEntity<List<Employee>> getEmployeeByDepartment(@PathVariable("dept") String department) throws DepartmentNotFoundException{
		List<Employee> result = departmentService.getEmployeesByDepartment(department);
		return new ResponseEntity<List<Employee>>(result, HttpStatus.OK);
	}
	//done
	@PostMapping("/register/DTE/{eid}/{did}")
	public ResponseEntity<String> addDeptToEmp(@PathVariable("eid") Integer empID, @PathVariable("did") Integer deptID) throws DepartmentNotFoundException, EmployeeNotFoundException{
		String result = departmentService.registerEmployeeToDepartment(empID, deptID);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	//done
	@GetMapping("/departments")
	public ResponseEntity<List<Department>> getallDepartment() {
		List<Department> result = departmentService.getAlldepartment();
		return new ResponseEntity<List<Department>>(result, HttpStatus.OK);
	}
	
	//done
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeEmployee(@PathVariable ("id") Integer id) throws  DepartmentNotFoundException{
		
		String result = departmentService.deletedepartment(id);
		
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}

}
