package com.aromit.routingds.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aromit.routingds.annotation.DataSource;
import com.aromit.routingds.datasource.DataSourceType;
import com.aromit.routingds.test.mapper.TestMapper;

@Service
@Transactional
public class TestService {
	@Autowired TestMapper testMapper;
	
	@DataSource(DataSourceType.PRIMARY)
	public List<Map<String, String>> showTables1(){
		return testMapper.showTables();
	}
	
	@DataSource(DataSourceType.SECONDARY)
	public List<Map<String, String>> showTables2(){
		return testMapper.showTables();
	}
	
	@DataSource(DataSourceType.TERTIARY)
	public List<Map<String, String>> showTables3(){
		return testMapper.showTables();
	}
}
