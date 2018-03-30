package com.city.testobj.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.city.testobj.dao.EmployeeDao;
import com.city.testobj.domain.Employee;
import com.city.testobj.exception.BussinessRuntimeException;
import com.city.testobj.exception.ErrorCode;
import com.city.testobj.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Resource
	private EmployeeDao employeeDao;

	public Integer add(Employee employee) {
		employee.setTimeCreated(new Date());
		employee.setTimeModified(new Date());
		int record = employeeDao.add(employee);
		if (record != 1) {
			throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}
		return employee.getId();
	}

}
