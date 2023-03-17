package com.employee.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.model.EmployeeDto;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService{
	
	@Autowired
	private EmployeeRepository edao;
	
	@Autowired
	private DepartmentRepository ddao;

	@Autowired
	private BCryptPasswordEncoder bcript;

	@Override
	public String registerEmployee(EmployeeDto empDto) {
		
		Employee emp = new Employee();
		emp.setEmailId(empDto.getEmailId());
		emp.setEmployeeFirstName(empDto.getEmployeeFirstName());
		emp.setEmployeeLastName(empDto.getEmployeeLastName());
		emp.setPhoneNumber(bcript.encode(empDto.getPhoneNumber()));
		
		edao.save(emp);
		return "Employee registered Successfully. EmployeeID is " + emp.getEmployeeID();
	}
	

	@Override
	public String deleteEmployee(Integer employeeID) throws EmployeeNotFoundException {
		Optional<Employee> validEmployee = edao.findById(employeeID);
		if(validEmployee.isPresent()) {
			Employee currentEmployee = validEmployee.get();
			edao.delete(currentEmployee);
			return "Employee deleted Successfully . EmployeeID is " + currentEmployee.getEmployeeID();
		}
		throw new EmployeeNotFoundException("Invalid EmployeeID: "+ employeeID);
	}

	@Override
	public String updateEmployee(Integer employeeID, EmployeeDto employee) throws EmployeeNotFoundException {
		Optional<Employee> validEmployee = edao.findById(employeeID);
		if(validEmployee.isPresent()) {
			Employee updatingemp = validEmployee.get();
			updatingemp.setEmployeeFirstName(employee.getEmployeeFirstName());
			updatingemp.setEmployeeLastName(employee.getEmployeeLastName());
			updatingemp.setEmailId(employee.getEmailId());
			updatingemp.setPhoneNumber(employee.getPhoneNumber());
			Employee updatedEmployee = edao.save(updatingemp);
			return "Employee updated Successfully. EmployeeID is " + updatingemp.getEmployeeID();
		}
		throw new EmployeeNotFoundException("Invalid EmployeeID: "+ employeeID);
	}

	@Override
	public Employee getEmployeeByID(Integer employeeID) throws EmployeeNotFoundException {
		Optional<Employee> validEmployee = edao.findById(employeeID);
		
		if(validEmployee.isPresent()) {
			Employee resultEmployee = validEmployee.get();
			return resultEmployee;
		}
		throw new EmployeeNotFoundException("Invalid EmployeeID: "+ employeeID);
	}

	@Override
	public List<Employee> getAllEmployeesList() {
		List<Employee> employees = edao.findAll();
		return employees;
	}

	@Override
	public List<Employee> getEmployeeByDepartment(Integer departmentID) throws DepartmentNotFoundException {
		Optional<Department> dept = ddao.findById(departmentID);
		
		if(dept.isPresent()) {
			Department department = dept.get();
			List<Employee> employees = department.getEmployees();
			return employees;
		}
		else throw new DepartmentNotFoundException("Invalid Department: "+ departmentID);
	}
	
	public List<Employee> getEmployeeWithSorting(String field){
		return edao.findAll(Sort.by(Sort.Direction.ASC,field));
	}
	
	public Page<Employee> getEmployeeWithPagination(int offset, int pageSize){
		Page<Employee> employees = edao.findAll(PageRequest.of(offset, pageSize));
		return employees;
	}

	@Override
	public Page<Employee> findEmployeeWithPaginationAndSorting(Integer offset, Integer pageSize, String field) {
		Page<Employee> employees = edao.findAll(PageRequest.of(offset, pageSize).withSort(Sort.by(field)));
		return employees;
	}
	
	// Sorting
		//  If you want to sort the employee table according it will sort
		// For that you have to use the field accordingly then it will show thw result 
		// use the get posting method.
		@Override
		public List<Employee> findEmployeewithsorting(String field){
			return edao.findAll(Sort.by(Sort.Direction.ASC,field));
		}
		
		
		//Pagination 
		// If you want to show the number employees as 3 for the pageSize is 3 .
		//pageSize means item per size.
		// and offset means element from which page we have to show elements as 1 ,2,3 from one page
		//																					4,5,6 from the 2nd page as it will show.
		// offset=0--> 1,2,3
		//offset=1 -->4,5,6
		//offset=2 -->7,8,9
		
		
		@Override
		public Page<Employee> getemployeebypagination(int offset, int pageSize) {
			Page<Employee> p=edao.findAll(PageRequest.of(offset, pageSize));
			return p;
		}
		
		//searching by native query
		@Override
		public List<Employee> searchAllbyfirstname(String s) {
			// TODO Auto-generated method stub
			List<Employee>l=edao.searchAllbyfirstname(s);
			return l;
		}

}
