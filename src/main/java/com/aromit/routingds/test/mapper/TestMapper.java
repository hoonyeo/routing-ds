package com.aromit.routingds.test.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface TestMapper {
	public List<Map<String,String>> showTables();
}
