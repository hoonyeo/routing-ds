package com.aromit.routingds;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.aromit.routingds.test.service.TestService;

@SpringBootApplication
public class RoutingDsApplication {
	private static Logger logger = LoggerFactory.getLogger(RoutingDsApplication.class);
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(RoutingDsApplication.class, args);
		
		TestService service =  context.getBean(TestService.class);
		logger.info(service.showTables1().toString());
		logger.info(service.showTables2().toString());
		logger.info(service.showTables3().toString());
	}
}
