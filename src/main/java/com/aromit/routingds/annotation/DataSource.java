package com.aromit.routingds.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
	com.aromit.routingds.datasource.DataSourceType value()
		default com.aromit.routingds.datasource.DataSourceType.PRIMARY;
}
