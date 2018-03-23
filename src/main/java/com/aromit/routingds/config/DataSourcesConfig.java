package com.aromit.routingds.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.aromit.routingds.datasource.DataSourceType;
import com.aromit.routingds.datasource.RoutingDataSource;

/**
 * Datasource 정의 클래스.
 * @author hoonyeo
 *
 */
@Configuration
public class DataSourcesConfig {
	@Autowired
	private DataSource primaryDataSource;
	
	@Autowired
	private DataSource secondaryDataSource;
	
	@Autowired
	private DataSource tertiaryDataSource;
	/**
	 * DataSoureProperties
	 * @return
	 */
	@Bean
	@Primary
	@ConfigurationProperties("datasource.primary-datasource")
	public DataSourceProperties primaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	/**
	 * DataSoureProperties
	 * @return
	 */
	@Bean
	@ConfigurationProperties("datasource.secondary-datasource")
	public DataSourceProperties secondaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	/**
	 * DataSoureProperties
	 * @return
	 */
	@Bean
	@ConfigurationProperties("datasource.tertiary-datasource")
	public DataSourceProperties tertiaryDataSourceProperties() {
		return new DataSourceProperties();
	}
	/**
	 * application.yml의 primary-datasource 설정 정보로 만들어진 datasource를 리턴한다.
	 * @return DataSource
	 */
	@Bean
	public DataSource primaryDataSource() {
		return primaryDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(BasicDataSource.class)
				.build();
	}
	/**
	 * application.yml의 secondary-datasource 설정 정보로 만들어진 datasource를 리턴한다.
	 * @return DataSource
	 */
	@Bean
	public DataSource secondaryDataSource() {
		return secondaryDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(BasicDataSource.class)
				.build();
	}
	/**
	 * application.yml의 tertiary-datasource 설정 정보로 만들어진 datasource를 리턴한다.
	 * @return DataSource
	 */
	@Bean
	public DataSource tertiaryDataSource() {
		return tertiaryDataSourceProperties()
				.initializeDataSourceBuilder()
				.type(BasicDataSource.class)
				.build();
	}
	/**
	 * routingDataSource
	 * @return
	 */
	@Bean
	public DataSource routingDataSource() {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.PRIMARY, primaryDataSource);
		targetDataSources.put(DataSourceType.SECONDARY, secondaryDataSource);
		targetDataSources.put(DataSourceType.TERTIARY, tertiaryDataSource);

		RoutingDataSource routingDataSource = new RoutingDataSource();
		routingDataSource.setTargetDataSources(targetDataSources);
		
		// Default primary-datasource
		routingDataSource.setDefaultTargetDataSource(primaryDataSource);
		return routingDataSource;
	}
}