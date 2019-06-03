package com.github.marschall.jfr.demo.ejb;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.interceptor.Interceptors;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.marschall.jfr.ejb.JfrInterceptor;
import com.github.marschall.jfrjdbctemplate.JfrJdbcOperations;

@Singleton
@Interceptors(JfrInterceptor.class)
public class SampleEjb {

  @Resource // default DataSource
  private DataSource dataSource;

  private JdbcOperations jdbcOperations;

  @PostConstruct
  public void ejbCreate() {
    this.jdbcOperations = new JfrJdbcOperations(new JdbcTemplate(this.dataSource));
  }

  public List<Integer> getRange() {
    return this.jdbcOperations.queryForList("SELECT X FROM SYSTEM_RANGE(1, 10)", Integer.class);
  }

}
