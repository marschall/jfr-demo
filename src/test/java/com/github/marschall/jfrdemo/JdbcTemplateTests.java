package com.github.marschall.jfrdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.github.marschall.jfrdemo.configuration.JdbcTemplateConfiguration;
import com.github.marschall.junit.jfr.JfrProfiled;

@JfrProfiled
@SpringJUnitConfig(JdbcTemplateConfiguration.class)
class JdbcTemplateTests {
  
  @Autowired
  private JdbcOperations jdbcOperations;
  
  @Autowired
  private NamedParameterJdbcOperations namedOperations;

  @Test
  void queryForListIndexed() {
    List<Long> ids = this.jdbcOperations.queryForList("SELECT parent_id"
        + " FROM parent_entity"
        + " WHERE parent_id > ?"
        + " ORDER BY parent_id",
        Long.class,
        0L);
    assertEquals(List.of(1L), ids);
  }
  
  @Test
  void queryForListNamed() {
    List<Long> ids = this.namedOperations.queryForList("SELECT parent_id"
        + " FROM parent_entity"
        + " WHERE parent_id > :id"
        + " ORDER BY parent_id",
        Map.of("id", 0L),
        Long.class);
    assertEquals(List.of(1L), ids);
  }

}
