package com.employee.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.employee.validation.DepartmentNames;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer departmentID;
	
	//custom Annotation
//	@DepartmentNames
	private String departmentName;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
	private List<Employee> Employees = new ArrayList<>();
	
}
