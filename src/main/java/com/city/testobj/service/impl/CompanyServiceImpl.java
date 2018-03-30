package com.city.testobj.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.city.testobj.dao.BankDao;
import com.city.testobj.dao.CompanyDao;
import com.city.testobj.dao.EmployeeDao;
import com.city.testobj.dao.TransferRecordDao;
import com.city.testobj.domain.Company;
import com.city.testobj.exception.BussinessRuntimeException;
import com.city.testobj.exception.ErrorCode;
import com.city.testobj.service.CompanyService;
import com.city.testobj.service.TransferFacade;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Resource
	private CompanyDao companyDao;

	@Resource
	private BankDao bankDao;

	@Resource
	private EmployeeDao employeeDao;

	@Resource
	private TransferRecordDao transferRecordDao;
	
	@Resource
	private TransferFacade transferFacade;

	public Integer add(Company company) {
		company.setTimeCreated(new Date());
		company.setTimeModified(new Date());
		int record = companyDao.add(company);
		if (record != 1) {
			throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}
		return company.getId();
	}

	@Transactional
	public boolean sendSalary(Integer companyId, Integer bankId, String employeeIds, BigDecimal amount) {

		String[] employeeIdArray = employeeIds.split(",");

		List<Integer> employeeIdList = new ArrayList<Integer>();
		for (String employeeId : employeeIdArray) {
			try {
				employeeIdList.add(Integer.parseInt(employeeId));
			} catch (Exception e) {
				throw new BussinessRuntimeException(ErrorCode.PARAM_VALIDATE_ERROR);
			}
		}

		int pageSize = 100;
		int page = (employeeIdList.size() + pageSize - 1) / pageSize;
		BlockingQueue<Integer> taskList = new LinkedBlockingQueue<Integer>();
		for (int index = 1; index <= page; index++) {
			taskList.offer(index);
		}

		int workCount = 5;
		workCount = Math.min(page, workCount);
		CountDownLatch latch = new CountDownLatch(workCount);
		Map<Integer,Boolean> map = new ConcurrentHashMap<Integer,Boolean>();
		while (workCount > 0) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					while (true) {
						try {
							Integer currentPage = taskList.poll(10, TimeUnit.MICROSECONDS);
							if (currentPage != null) {
								List<Integer> employeeList = employeeIdList.subList((currentPage - 1) * pageSize, Math.min((currentPage - 1) * pageSize + pageSize, employeeIdList.size()));
								for (Integer employeeId : employeeList) {
									try {
										transferFacade.transfer(employeeId, bankId, amount);
									} catch (Exception e) {
										map.put(currentPage, Boolean.FALSE);
										break;
									}
								}
								if(map.get(currentPage) == null){
									map.put(currentPage, Boolean.TRUE);
								}
							} else {
								break;
							}
						} catch (Exception e) {

						}
					}
					latch.countDown();
				}
			}).start();
			workCount--;
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		boolean result = true;
		for(Map.Entry<Integer, Boolean> entry : map.entrySet()){
			result = result && entry.getValue();
		}
		if(!result){
			throw new BussinessRuntimeException(ErrorCode.DB_OPERATE_ERROR);
		}

		return true;
	}

}
