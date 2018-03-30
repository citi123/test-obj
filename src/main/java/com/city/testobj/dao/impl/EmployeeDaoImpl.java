package com.city.testobj.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.city.testobj.dao.BaseDao;
import com.city.testobj.dao.EmployeeDao;
import com.city.testobj.domain.Employee;

@Repository
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {
	
	
	public Employee getById(Integer id) {
		return writeSqlSessionTemplate.selectOne("EmployeeDao.getById", id);
	}

	public int add(Employee employee) {
		return writeSqlSessionTemplate.insert("EmployeeDao.add", employee);
	}

	public int out(int employeeId, BigDecimal amount) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", employeeId);
		params.put("amount", amount.abs());
		params.put("timeModified", new Date());
		return writeSqlSessionTemplate.update("EmployeeDao.out",params);
	}

	public int in(int employeeId, BigDecimal amount) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", employeeId);
		params.put("amount", amount.abs());
		params.put("timeModified", new Date());
		return writeSqlSessionTemplate.update("EmployeeDao.in",params);
	}

}
