package com.city.testobj.dao;

import java.math.BigDecimal;

import com.city.testobj.domain.Bank;

public interface BankDao {
	int add(Bank bank);
	
	int out(Integer bankId,BigDecimal amount);
	int in(Integer bankId,BigDecimal amount);
}
