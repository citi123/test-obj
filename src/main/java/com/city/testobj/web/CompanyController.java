package com.city.testobj.web;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.city.testobj.domain.Company;
import com.city.testobj.service.CompanyService;
import com.city.testobj.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/company",method = RequestMethod.POST)
@Api(value = "企业",tags = "企业")
public class CompanyController {

	@Resource
	private CompanyService companyService;
	
	@RequestMapping("/add")
	@ApiOperation(value = "新增企业")
	public ApiResponse<Integer> add(Company company){
		return ApiResponse.success(companyService.add(company));
	}
	
	@RequestMapping("/sendSalary")
	@ApiOperation(value = "发工资")
	public ApiResponse<Boolean> sendSalary(Integer companyId,Integer bankId,String employeeIds,BigDecimal amount){
		return ApiResponse.success(companyService.sendSalary(companyId, bankId, employeeIds, amount));
	}
}
