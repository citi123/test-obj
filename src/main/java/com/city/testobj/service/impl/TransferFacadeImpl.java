package com.city.testobj.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.city.testobj.dao.BankDao;
import com.city.testobj.dao.EmployeeDao;
import com.city.testobj.dao.TransferRecordDao;
import com.city.testobj.domain.TransferRecord;
import com.city.testobj.exception.BussinessRuntimeException;
import com.city.testobj.exception.ErrorCode;
import com.city.testobj.service.TransferFacade;

@Service
public class TransferFacadeImpl implements TransferFacade {

	
	@Resource
	private BankDao bankDao;

	@Resource
	private EmployeeDao employeeDao;

	@Resource
	private TransferRecordDao transferRecordDao;
	
	@Override
	@Transactional
	public void transfer(Integer inId, Integer outId, BigDecimal amount) {
		int record = bankDao.out(outId, amount);
		if (record != 1) {
			 throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}
		record = employeeDao.in(inId, amount);
		if (record != 1) {
			 throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}

		TransferRecord transferRecord = new TransferRecord();
		transferRecord.setInid(outId);
		transferRecord.setInType(1);
		transferRecord.setOutId(inId);
		transferRecord.setOutType(2);
		transferRecord.setAmount(amount);
		transferRecord.setTimeCreated(new Date());
		record = transferRecordDao.add(transferRecord);

		if (record != 1) {
			 throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}

	}

}
