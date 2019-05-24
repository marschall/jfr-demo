package com.github.marschall.jfr.demo.web.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class JdbcTemplateConfiguration {
 
  @Autowired
  private DataSource dataSource;
  
  @Bean
  public JdbcOperations jdbcOperations() {
    return new JdbcTemplate(this.dataSource);
  }
  
  @Bean
  public PlatformTransactionManager txManager() {
    return new DataSourceTransactionManager(this.dataSource);
  }

}
