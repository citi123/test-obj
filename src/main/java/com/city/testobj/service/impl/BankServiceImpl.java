package com.city.testobj.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.city.testobj.dao.BankDao;
import com.city.testobj.domain.Bank;
import com.city.testobj.exception.BussinessRuntimeException;
import com.city.testobj.exception.ErrorCode;
import com.city.testobj.service.BankService;

@Service
public class BankServiceImpl implements BankService {

	@Resource
	private BankDao bankDao;

	public Integer add(Bank bank) {
		// todo check param
		bank.setTimeCreated(new Date());
		bank.setTimeModified(new Date());
		int record = bankDao.add(bank);
		
		if (record != 1) {
			throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}
		return bank.getId();
	}

}
