package com.city.testobj.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

public class BaseDao {
	@Resource
    protected SqlSessionTemplate readSqlSessionTemplate;

    @Resource
    protected SqlSessionTemplate writeSqlSessionTemplate;
}
