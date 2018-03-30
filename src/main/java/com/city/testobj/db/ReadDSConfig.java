package com.city.testobj.db;

import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.bind.PropertySourceUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.MutablePropertySources;

/**
 * desc: 读数据源
 * author: alaskwang
 * date: 2017/8/24
 */

@Configuration
public class ReadDSConfig extends DSConfig{

    @Bean(name="readDS")
    public DataSource init(){
        // 读取配置文件获取更多数据源
        MutablePropertySources propertySources = loadPropertySource();
        Map<String, Object> dsMap = PropertySourceUtils.getSubProperties(propertySources, "datasource.",  "readDS.");
        return createDatasource(dsMap);
    }

    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("readDS") DataSource dataSource) throws Exception {
        return createFactory(dataSource);
    }

    @Bean(name = "readSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("readSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
