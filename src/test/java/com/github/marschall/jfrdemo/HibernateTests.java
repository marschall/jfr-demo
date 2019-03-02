package com.github.marschall.jfrdemo;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.marschall.jfrdemo.configuration.HibernateConfiguration;
import com.github.marschall.junit.jfr.JfrProfiled;

@JfrProfiled
@Transactional
@SpringJUnitConfig(HibernateConfiguration.class)
class HibernateTests {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void test() {
    
  }

}
