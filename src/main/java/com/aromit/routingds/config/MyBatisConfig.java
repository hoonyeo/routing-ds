package com.aromit.routingds.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement(order=2)
@MapperScan(basePackages = "com.aromit.routingds")
public class MyBatisConfig {
	private final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
	
	private final static String DOMAIN_PACKAGE_LOCATIONS = "com.aromit.routingds";
	private final static String CONFIG_LOCATION = "classpath:com/aromit/routingds/config/mybatis.xml";
	private final static String MAPPER_LOCATIONS = "classpath:com/aromit/routingds/**/mapper/**/*.xml";
	
	@Autowired DataSource routingDataSource;
	
	@Bean
	public SqlSessionFactory sqlSessionFactory() {
		try {
			PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(MAPPER_LOCATIONS);

			SqlSessionFactoryBean obj = new SqlSessionFactoryBean();
			obj.setDataSource(routingDataSource);
			obj.setConfigLocation(resolver.getResource(CONFIG_LOCATION));
			obj.setTypeAliasesPackage(DOMAIN_PACKAGE_LOCATIONS);
			obj.setMapperLocations(resources);

			return obj.getObject();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Bean
	public SqlSessionTemplate sqlSession() {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(routingDataSource);
	}
}
