package com.meter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.codahale.metrics.MetricRegistry;

@Configuration
@EnableWebMvc
@ComponentScan({ "com.meter" })
@PropertySource(value = { "classpath:meter.properties" })
public class MeterConfig {
	@Bean
	public MetricRegistry getMetrics(){
		return new MetricRegistry();
	}
}
