package com.city.testobj.dao.impl;

import org.springframework.stereotype.Repository;

import com.city.testobj.dao.BaseDao;
import com.city.testobj.dao.TransferRecordDao;
import com.city.testobj.domain.TransferRecord;

@Repository
public class TransferRecordDaoImpl extends BaseDao implements TransferRecordDao {

	public int add(TransferRecord transferRecord) {
		return writeSqlSessionTemplate.insert("TransferRecordDao.add", transferRecord);
	}

}
