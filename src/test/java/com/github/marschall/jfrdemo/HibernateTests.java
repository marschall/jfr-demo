package com.github.marschall.jfrdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.marschall.jfrdemo.configuration.HibernateConfiguration;
import com.github.marschall.jfrdemo.entities.ParentEntity;
import com.github.marschall.junit.jfr.JfrProfiled;

@JfrProfiled
@Transactional
@SpringJUnitConfig(HibernateConfiguration.class)
class HibernateTests {
  
  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void find() {
    ParentEntity parent = this.entityManager.find(ParentEntity.class, 1L);
    assertNotNull(parent);
    assertEquals(1L, parent.getParentId());
  }

}
