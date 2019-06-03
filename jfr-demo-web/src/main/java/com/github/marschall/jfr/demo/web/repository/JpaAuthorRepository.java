package com.github.marschall.jfr.demo.web.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.github.marschall.jfr.demo.web.entity.Author;
import com.github.marschall.jfr.demo.web.model.AuthorsViewModel;
import com.github.marschall.jfr.demo.web.model.AuthorsViewModelEntry;

public class JpaAuthorRepository implements AuthorRepository {

  private final EntityManager entityManager;

  public JpaAuthorRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public AuthorsViewModel loadModel() {
    List<AuthorsViewModelEntry> entries = new ArrayList<>();
    for (Object each : this.entityManager.createQuery("SELECT a FROM Author a").getResultList()) {
      Author author = (Author) each;
      int articleCount = author.getArticles().size();
      int commentCount = author.getArticles().stream()
              .mapToInt(a -> a.getComments().size())
              .sum();
      AuthorsViewModelEntry entry = new AuthorsViewModelEntry(author.getName(), articleCount, commentCount);
      entries.add(entry);
    }

//    List<Author> collect = this.entityManager.createQuery("SELECT a FROM Author a")
//      .getResultStream()
////      .map(AuthorsViewModel.class::cast)
//      .map(e -> (Author) e)
//      .collect(Collectors.toList());
    // TODO Auto-generated method stub
    return new AuthorsViewModel(entries);
  }

}
