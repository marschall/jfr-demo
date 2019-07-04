package com.github.marschall.jfr.demo.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.marschall.jfr.demo.web.repository.AuthorRepository;
import com.github.marschall.jfr.demo.web.service.AuthorService;

@Configuration
@EnableTransactionManagement
public class ApplicationConfiguration {

  @Autowired
  private AuthorRepository authorRepository;

  @Bean
  public AuthorService authorService() {
    return new AuthorService(this.authorRepository);
  }

}
