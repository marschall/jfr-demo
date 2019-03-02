package com.github.marschall.jfrdemo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.marschall.jfrjdbctemplate.JfrJdbcOperations;

@Configuration
@Import(H2Configuration.class)
public class JdbcTemplateConfiguration {
  
  @Autowired
  private DataSource dataSource;
  
  @Bean
  public JdbcOperations jdbcOperations() {
    return new JfrJdbcOperations(new JdbcTemplate(this.dataSource));
  }

}
