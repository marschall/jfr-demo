package com.github.marschall.jfr.demo.web.repositoryy;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.github.marschall.jfr.demo.web.model.AuthorsViewModel;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

  private final JdbcOperations jdbcOperations;

  public JdbcAuthorRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  @Override
  public AuthorsViewModel loadModel() {
    // TODO Auto-generated method stub
    return null;
  }

}
