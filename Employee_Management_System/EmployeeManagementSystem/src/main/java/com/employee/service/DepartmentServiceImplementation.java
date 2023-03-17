package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.exception.DepartmentNotFoundException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.model.Department;
import com.employee.model.Employee;
import com.employee.repository.DepartmentRepository;
import com.employee.repository.EmployeeRepository;

@Service
public class DepartmentServiceImplementation implements DepartmentService{
	
	@Autowired
	private DepartmentRepository ddao;
	
	@Autowired
	private EmployeeRepository edao;
	
	
	@Override
	public String registerDepartment(Department department) {
		ddao.save(department);
		return "Department registered Successfully " + department.getDepartmentName()  + " Department";
	}

	
	@Override
	public List<Department> getAlldepartment() {
		List<Department> d=ddao.findAll();
		return d;
	}

	
	@Override
	public String deletedepartment(Integer departmentId) throws DepartmentNotFoundException {
		Optional<Department> department = ddao.findById(departmentId);
		if(department.isPresent()) {
			Department currentd = department.get();
			ddao.delete(currentd);
			return "Successfully deleted Department. DepartmentID is " + currentd.getDepartmentID();
		}
		throw new DepartmentNotFoundException("Invalid EmployeeID: "+ departmentId);
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) throws DepartmentNotFoundException{
		Department dept = ddao.findByDepartmentName(department);
		if(dept == null) {
			throw new DepartmentNotFoundException("Invalid Department: "+ department);
		}
		return dept.getEmployees();
	}

	@Override
	public String registerEmployeeToDepartment(Integer employeeID, Integer departmentID) throws EmployeeNotFoundException, DepartmentNotFoundException {
		Department validateDepartment = ddao.findById(departmentID).get();
		Employee validateEmployee = edao.findById(employeeID).get();
		if(validateDepartment == null) {
			throw new DepartmentNotFoundException("Invalid DepartmentID: "+ departmentID);
		}
		if(validateEmployee == null) {
			throw new EmployeeNotFoundException("Invalid EmployeeID: " + employeeID);
		}
		else {
			validateDepartment.getEmployees().add(validateEmployee);
			validateEmployee.setDepartment(validateDepartment);
			ddao.save(validateDepartment);
			return "Employee " + validateEmployee.getEmployeeFirstName() + " successfully registered to Department " + validateDepartment.getDepartmentName();
		}
	}

	
	

}
