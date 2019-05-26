package com.github.marschall.jfr.demo.web.repositoryy;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import com.github.marschall.jfr.demo.web.model.AuthorsViewModel;
import com.github.marschall.jfr.demo.web.model.AuthorsViewModelEntry;

@Repository
public class JdbcAuthorRepository implements AuthorRepository {

  private final JdbcOperations jdbcOperations;

  public JdbcAuthorRepository(JdbcOperations jdbcOperations) {
    this.jdbcOperations = jdbcOperations;
  }

  @Override
  public AuthorsViewModel loadModel() {
    List<Integer> authorIds = this.jdbcOperations.queryForList("SELECT id FROM author", Integer.class);
    List<AuthorsViewModelEntry> entries = new ArrayList<>(authorIds.size());
    for (Integer authorId : authorIds) {
      String authorName = this.jdbcOperations.queryForObject("SELECT name FROM author WHERE id = ?", String.class, authorId);

      List<Integer> articleIds = this.jdbcOperations.queryForList("SELECT id FROM article WHERE author_id = ?", Integer.class, authorId);
      int articleCount = articleIds.size();

      int commentCount = 0;
      for (Integer articleId : articleIds) {
        List<Integer> commentIds = this.jdbcOperations.queryForList("SELECT id FROM comment WHERE article_id = ?", Integer.class, articleId);
        commentCount += commentIds.size();
      }

      AuthorsViewModelEntry entry = new AuthorsViewModelEntry(authorName, articleCount, commentCount);
      entries.add(entry);
    }
    return new AuthorsViewModel(entries);
  }

}
