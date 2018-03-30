package com.city.testobj.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.city.testobj.dao.BankDao;
import com.city.testobj.dao.BaseDao;
import com.city.testobj.domain.Bank;

@Repository
public class BankDaoImpl extends BaseDao implements BankDao {

	public int add(Bank bank) {
		return writeSqlSessionTemplate.insert("BankDao.add", bank);
	}

	public int out(Integer bankId, BigDecimal amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", bankId);
		params.put("amount", amount.abs());
		params.put("timeModified", new Date());
		return writeSqlSessionTemplate.update("BankDao.out", params);
	}

	public int in(Integer bankId, BigDecimal amount) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", bankId);
		params.put("amount", amount.abs());
		params.put("timeModified", new Date());
		return writeSqlSessionTemplate.update("BankDao.in", params);
	}

}
