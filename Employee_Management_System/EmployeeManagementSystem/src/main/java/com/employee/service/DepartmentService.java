package com.employee.service;

import java.util.List;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.model.Department;
import com.employee.model.Employee;

public interface DepartmentService {
	
	public String registerDepartment(Department dept);
	
	public String registerEmployeeToDepartment(Integer employeeID, Integer departmentID) throws EmployeeNotFoundException,DepartmentNotFoundException;
	
	public String deletedepartment(Integer departmentId) throws DepartmentNotFoundException;
	
	public List<Employee> getEmployeesByDepartment(String department) throws DepartmentNotFoundException;
    
	public List<Department> getAlldepartment();
	
	
}
