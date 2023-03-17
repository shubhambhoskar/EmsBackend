package com.employee.service;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.employee.model.Employee;
import com.employee.model.EmployeePage;
import com.employee.model.EmployeeSearchCriteria;
import com.employee.repository.EmployeeCriteriaRepository;
import com.employee.repository.EmployeeRepository;

@Service
public class CriteriaEmployeeService {

	private EmployeeRepository erepo;
	private EmployeeCriteriaRepository ecriteria;
	
	
	public CriteriaEmployeeService(EmployeeRepository erepo, EmployeeCriteriaRepository ecriteria) {
		super();
		this.erepo = erepo;
		this.ecriteria = ecriteria;
	}

	public Page<Employee> getEmployees(EmployeePage employePage,EmployeeSearchCriteria esearch){
		
		return ecriteria.findallWithFilter(employePage, esearch);
	}

//
//	public Employee addEmpoyee(Employee emp ) {
//		
//		return erepo.save(emp);
//	}




}
