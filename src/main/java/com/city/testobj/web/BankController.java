package com.city.testobj.web;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.city.testobj.domain.Bank;
import com.city.testobj.service.BankService;
import com.city.testobj.util.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/bank", method = RequestMethod.POST)
@Api(tags = { "银行卡相关" })
public class BankController {

	@Resource
	private BankService bankService;

	@ApiOperation(value = "新增银行卡", notes = "新增银行卡")
	@RequestMapping("add")
	public ApiResponse<Integer> add(Bank bank) {
		return ApiResponse.success(bankService.add(bank));
	}
}
