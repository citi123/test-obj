package com.city.testobj.db;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import com.city.testobj.exception.BussinessRuntimeException;
import com.city.testobj.exception.ErrorCode;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * desc: 数据源基类
 * date: 2017/8/24
 */
public abstract class DSConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DSConfig.class);

    private static final String packagePath = "com.city.testobj.domain";

    private static final String resourcePath = "classpath:/mapper/*.xml";

    // 如配置文件中未指定数据源类型，使用该默认值
    protected static final String DATASOURCE_TYPE_DEFAULT = "com.zaxxer.hikari.HikariDataSource";

    public abstract DataSource init();

    public MutablePropertySources loadPropertySource() {
        try {
            InputStream is = new ClassPathResource("datasource.properties").getInputStream();
            Properties properties = new Properties();
            properties.load(is);
            PropertySource propertySource = new PropertiesPropertySource("datasource", properties);
            MutablePropertySources propertySources = new MutablePropertySources();
            propertySources.addFirst(propertySource);
            return propertySources;
        } catch (Exception e) {
            throw new RuntimeException("获取配置失败", e);
        }
    }

    public DataSource createDatasource(Map<String, Object> dsMap) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl((String) dsMap.get("url"));
        config.setUsername((String) dsMap.get("username"));
        config.setPassword((String) dsMap.get("password"));
        config.setDriverClassName((String) dsMap.get("driverClassName"));
        config.setConnectionTimeout(Long.valueOf(dsMap.get("connectionTimeout").toString()));
        config.setIdleTimeout(Long.valueOf(dsMap.get("idleTimeout").toString()));
        config.setMaxLifetime(Long.valueOf(dsMap.get("maxLifetime").toString()));
        config.setMaximumPoolSize(Integer.valueOf(dsMap.get("maximumPoolSize").toString()));
        config.setMinimumIdle(Integer.valueOf(dsMap.get("minimumIdle").toString()));
        config.setPoolName(dsMap.get("poolName").toString());
        config.setValidationTimeout(Long.valueOf(dsMap.get("validationTimeout").toString()));
        config.setLeakDetectionThreshold(Long.valueOf(dsMap.get("leakDetectionThreshold").toString()));
        config.addDataSourceProperty("prepStmtCacheSize", dsMap.get("prepStmtCacheSize").toString());
        config.addDataSourceProperty("prepStmtCacheSqlLimit", dsMap.get("prepStmtCacheSqlLimit").toString());
        config.addDataSourceProperty("cachePrepStmts", dsMap.get("cachePrepStmts").toString());
        config.addDataSourceProperty("useServerPrepStmts", dsMap.get("useServerPrepStmts").toString());
        return new HikariDataSource(config);
    }

    public SqlSessionFactory createFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage(packagePath);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(resourcePath));
        bean.setDatabaseIdProvider(getDatabaseIdProvider());
        SqlSessionFactory sf = bean.getObject();
        String databaseId = sf.getConfiguration().getDatabaseId();
        if(StringUtils.isEmpty(databaseId)){
            throw new BussinessRuntimeException(ErrorCode.DB_CONFIG_ERROR);
        }
        return sf;
    }

    public VendorDatabaseIdProvider getDatabaseIdProvider() {
        VendorDatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        properties.put("MySQL", "mysql");
        properties.put("Apache Derby", "derby");
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }
}
