package com.city.testobj.service;

import java.math.BigDecimal;

public interface TransferFacade {
	void transfer(Integer inId,Integer outId,BigDecimal amount);
}
