package com.aromit.routingds.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.aromit.routingds.annotation.DataSource;
import com.aromit.routingds.datasource.ContextHolder;
import com.aromit.routingds.datasource.DataSourceType;

@Aspect
@Component
@Order(value=1)
public class RoutingDataSourceLogging implements InitializingBean {
	private Logger log = LoggerFactory.getLogger(RoutingDataSourceLogging.class);
	
	@Around("execution(* com.aromit..*Service.*(..))")
	public Object doServiceProfiling(ProceedingJoinPoint joinPoint) throws Throwable {
		log.debug("Service start");
		
		final String methodName = joinPoint.getSignature().getName();
		final MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Method method = methodSignature.getMethod();
		if (method.getDeclaringClass().isInterface()) {
			method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}
		
		DataSource dataSource = (DataSource) method.getAnnotation(DataSource.class);
		if (dataSource != null) {
			ContextHolder.setDataSourceType(dataSource.value());
		} else {
			if (!(method.getName().startsWith("get") || method.getName().startsWith("select"))) {
				ContextHolder.setDataSourceType(DataSourceType.PRIMARY);
			}
		}
		log.debug("DataSource ===> " + ContextHolder.getDataSourceType());
		Object returnValue = joinPoint.proceed();
		ContextHolder.clearDataSourceType();
		log.debug("Service end");
		return returnValue;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
	}
}
