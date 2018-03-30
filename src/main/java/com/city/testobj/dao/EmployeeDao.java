package com.city.testobj.dao;

import java.math.BigDecimal;

import com.city.testobj.domain.Employee;

public interface EmployeeDao {
	Employee getById(Integer id);
	
	int add(Employee employee);
	
	int out(int employeeId,BigDecimal amount);
	
	int in(int employeeId,BigDecimal amount);
}
