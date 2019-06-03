package com.github.marschall.jfr.demo.web.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class Author {

  @Id
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "author-sequence-generator"
  )
  @SequenceGenerator(
      name = "author-sequence-generator",
      sequenceName = "AUTHOR_ID",
      allocationSize = 1
  )
  private Integer id;

  @Column
  private String name;

  @OneToMany
  @JoinColumn(name = "AUTHOR_ID")
  private Set<Article> articles = new HashSet<>();

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Article> getArticles() {
    return this.articles;
  }

  public void setArticles(Set<Article> articles) {
    this.articles = articles;
  }


}
