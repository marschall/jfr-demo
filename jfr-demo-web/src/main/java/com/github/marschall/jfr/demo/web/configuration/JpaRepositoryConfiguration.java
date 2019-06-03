package com.github.marschall.jfr.demo.web.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.marschall.jfr.demo.web.repository.AuthorRepository;
import com.github.marschall.jfr.demo.web.repository.JpaAuthorRepository;

@Configuration
public class JpaRepositoryConfiguration {
  
  @PersistenceContext
  private EntityManager entityManager;
  
  @Bean
  public AuthorRepository authorRepository() {
    return new JpaAuthorRepository(this.entityManager);
  }

}
