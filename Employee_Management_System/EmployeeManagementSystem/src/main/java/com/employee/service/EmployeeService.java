package com.employee.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.model.Employee;
import com.employee.model.EmployeeDto;


public interface EmployeeService {
	
	public String registerEmployee(EmployeeDto employee);
	
	public String deleteEmployee(Integer employeeID) throws EmployeeNotFoundException;
	
	public String updateEmployee(Integer employeeID, EmployeeDto employee) throws EmployeeNotFoundException;
	
	public Employee getEmployeeByID(Integer employeeID) throws EmployeeNotFoundException;
	
	public List<Employee> getEmployeeByDepartment(Integer departmentID) throws DepartmentNotFoundException;
	
	public List<Employee> getAllEmployeesList();
	
	public Page<Employee> findEmployeeWithPaginationAndSorting(Integer offset, Integer pageSize, String field);

	public List<Employee> findEmployeewithsorting(String field);
	
	public Page<Employee> getemployeebypagination(int offset, int pageSize );
	
	public List<Employee> searchAllbyfirstname(String s);
}
