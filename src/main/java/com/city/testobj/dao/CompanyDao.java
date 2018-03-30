package com.city.testobj.dao;

import com.city.testobj.domain.Company;

public interface CompanyDao {
	Company getById(Integer id);
	
	int add(Company company);
}
