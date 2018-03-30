package com.city.testobj.dao.impl;

import org.springframework.stereotype.Repository;

import com.city.testobj.dao.BaseDao;
import com.city.testobj.dao.CompanyDao;
import com.city.testobj.domain.Company;

@Repository
public class CompanyDaoImpl extends BaseDao implements CompanyDao {

	public Company getById(Integer id) {
		return writeSqlSessionTemplate.selectOne("CompanyDao.getById", id);
	}

	public int add(Company company) {
		return writeSqlSessionTemplate.insert("CompanyDao.add", company);
	}

}
