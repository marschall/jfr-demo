package com.github.marschall.jfr.demo.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;

import com.github.marschall.jfr.demo.web.repository.AuthorRepository;
import com.github.marschall.jfr.demo.web.repository.JdbcAuthorRepository;

@Configuration
public class JdbcRepositoryConfiguration {
  
  @Autowired
  private JdbcOperations jdbcOperations;
  
  @Bean
  public AuthorRepository authorRepository() {
    return new JdbcAuthorRepository(this.jdbcOperations);
  }

}
