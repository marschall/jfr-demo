package com.github.marschall.jfrdemo;

import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.marschall.jfrdemo.configuration.JdbcTemplateConfiguration;
import com.github.marschall.junit.jfr.JfrProfiled;

import static org.hamcrest.MatcherAssert.assertThat;

@JfrProfiled
@SpringJUnitConfig(JdbcTemplateConfiguration.class)
class JdbcTemplateTests {
  
  @Autowired
  private JdbcOperations jdbcOperations;

  @Test
  void queryForList() {
    List<Long> ids = this.jdbcOperations.queryForList("SELECT parent_id FROM parent_entity ORDER BY parent_id", Long.class);
    assertThat(ids, hasSize(1));
  }

}
