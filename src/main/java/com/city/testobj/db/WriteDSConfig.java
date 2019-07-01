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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * desc: 写数据源
 * date: 2017/8/23
 */
@Configuration
public class WriteDSConfig extends DSConfig{

    @Bean(name="writeDS")
    @Primary
    public DataSource init(){
        // 读取配置文件获取更多数据源
        MutablePropertySources propertySources = loadPropertySource();
        Map<String, Object> dsMap = PropertySourceUtils.getSubProperties(propertySources, "datasource.",  "writeDS.");
        return createDatasource(dsMap);
    }

    @Bean(name = "writeSqlSessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier("writeDS") DataSource dataSource) throws Exception {
        return createFactory(dataSource);
    }

    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager transactionManager(@Qualifier("writeDS") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "writeSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("writeSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
