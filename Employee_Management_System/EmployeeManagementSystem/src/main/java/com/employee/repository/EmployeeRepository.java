package com.employee.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	
		//SEARCH NATIVE QUERY 
		@Query(value = "SELECT * FROM Employee WHERE employee_first_name LIKE %?1%",nativeQuery = true)
		public List<Employee> searchAllbyfirstname(String string);
		
		
		
		 public Employee findByEmailId(String email);
}
