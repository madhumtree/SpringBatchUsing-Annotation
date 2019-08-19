package com.tivo.metadata.max.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfig {
	
	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.work")
	public DataSourceProperties workDataSourceProperties() {
		return new DataSourceProperties();
	}

	@Bean
	@Primary
	@ConfigurationProperties("app.datasource.work.configuration")
	public HikariDataSource workDataSource() {
		return workDataSourceProperties().initializeDataSourceBuilder()
				.type(HikariDataSource.class).build();
	}
	
	
	
	  @Bean public JdbcTemplate jdbcTemplate() { return new
	 JdbcTemplate(workDataSource()); }
	 
	
	
}
