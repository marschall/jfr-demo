package com.github.marschall.jfrdemo.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.github.marschall.jfr.jdbctemplate.JfrJdbcOperations;
import com.github.marschall.jfr.jdbctemplate.JfrNamedParameterJdbcOperations;

@Configuration
@Import(H2Configuration.class)
public class JdbcTemplateConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean
  public JdbcOperations jdbcOperations() {
    var delegate = new JdbcTemplate(this.dataSource);
    return new JfrJdbcOperations(delegate);
  }

  @Bean
  public NamedParameterJdbcOperations namedJdbcOperations() {
    var delegate = new NamedParameterJdbcTemplate(this.jdbcOperations());
    return new JfrNamedParameterJdbcOperations(delegate);
  }

}
