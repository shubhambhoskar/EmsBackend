package com.employee.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.employee.model.Employee;
import com.employee.model.EmployeePage;
import com.employee.model.EmployeeSearchCriteria;

@Repository
public class EmployeeCriteriaRepository {

	private final EntityManager entityManager;
	
	private final CriteriaBuilder criteriaBuilder;

	public EmployeeCriteriaRepository(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
		this.criteriaBuilder = entityManager.getCriteriaBuilder();
	}
	
	public Page<Employee> findallWithFilter(EmployeePage employeePage, EmployeeSearchCriteria employeeSearchCriteria){
		
		CriteriaQuery<Employee> criteriaQuery=criteriaBuilder.createQuery(Employee.class);
		Root<Employee> employeeeRoot=criteriaQuery.from(Employee.class);
		Predicate predicate=getPredicate(employeeSearchCriteria,employeeeRoot);
		
		criteriaQuery.where((Expression<Boolean>) predicate);
		
		setOrder(employeePage,criteriaQuery,employeeeRoot);
	
		TypedQuery<Employee> typedQuery=entityManager.createQuery(criteriaQuery);
		typedQuery.setFirstResult(employeePage.getPageNumber()*employeePage.getPageNumber());
		typedQuery.setMaxResults(employeePage.getPageSize());
		
		Pageable pageable=getPageable(employeePage);
	
		long employeeCount=getemployeeCount(predicate);
		
		return new PageImpl<>(typedQuery.getResultList(),pageable, employeeCount);
	}

	

	private Predicate getPredicate(EmployeeSearchCriteria employeeSearchCriteria, Root<Employee> employeeeRoot) {
		// TODO Auto-generated method stub
		
		List<Predicate> predicates=new ArrayList();
		
		// for filter value are not null
		if(Objects.nonNull(employeeSearchCriteria.getEmployeeFirstName())) {
			predicates.add(
					(Predicate) criteriaBuilder.like(employeeeRoot.get("employeeFirstName"),"%"+ employeeSearchCriteria.getEmployeeFirstName() + "%") 
					);
			
		}
		
		// for filter value are not null
				if(Objects.nonNull(employeeSearchCriteria.getEmployeeFirstName())) {
					predicates.add(
							(Predicate) criteriaBuilder.like(employeeeRoot.get("employeeLastName"),"%"+ employeeSearchCriteria.getEmployeeFirstName() + "%") 
							);
					
				}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
	
	private void setOrder(EmployeePage employeePage, CriteriaQuery<Employee> criteriaQuery,
			Root<Employee> employeeeRoot) {
		if(employeePage.getSortDirection().equals(Sort.Direction.ASC)) {
			criteriaQuery.orderBy(criteriaBuilder.asc(employeeeRoot.get(employeePage.getSortBy())));
		}else {
			criteriaQuery.orderBy(criteriaBuilder.desc(employeeeRoot.get(employeePage.getSortBy())));

		}
		
	}

	private Pageable getPageable(EmployeePage employeePage) {
		// TODO Auto-generated method stub
		
		Sort sort=Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
		
		
		return (Pageable) PageRequest.of(employeePage.getPageNumber(), employeePage.getPageSize(),sort);
	}
	

	private long getemployeeCount(Predicate predicate) {
		CriteriaQuery<Long> countQuery=criteriaBuilder.createQuery(Long.class);
		Root<Employee> countRoot=countQuery.from(Employee.class);
		countQuery.select(criteriaBuilder.count(countRoot)).where((Expression<Boolean>) predicate);
		
		return entityManager.createQuery(countQuery).getSingleResult();
	}
	
	
}
