package com.city.testobj.service;

import java.math.BigDecimal;

import com.city.testobj.domain.Company;

public interface CompanyService {
	Integer add(Company company);
	
	
	boolean sendSalary(Integer companyId,Integer bankId,String employeeIds,BigDecimal amount);
}
