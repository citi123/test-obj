package com.city.testobj.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.city.testobj.domain.Employee;
import com.city.testobj.service.EmployeeService;
import com.city.testobj.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/employee", method = RequestMethod.POST)
@Api("员工")
public class EmployeeController {

	@Resource
	private EmployeeService employeeService;

	@RequestMapping("/add")
	@ApiOperation(value = "新增员工")
	public ApiResponse<Integer> add(Employee employee) {
		return ApiResponse.success(employeeService.add(employee));
	}
}
