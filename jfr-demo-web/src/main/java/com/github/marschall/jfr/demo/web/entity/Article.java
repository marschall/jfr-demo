package com.github.marschall.jfr.demo.web.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.OffsetDateTime;
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
public class Article {

  @Id
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "article-sequence-generator"
  )
  @SequenceGenerator(
      name = "article-sequence-generator",
      sequenceName = "ARTICLE_ID",
      allocationSize = 1
  )
  private Integer id;

  @Column
  private String title;

  @Column(name =  "PUBLISHED_AT")
  private OffsetDateTime publishedAt;

  @OneToMany
  @JoinColumn(name = "ARTICLE_ID")
  private Set<Comment> comments = new HashSet<>();

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public OffsetDateTime getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(OffsetDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }

  public Set<Comment> getComments() {
    return this.comments;
  }

  public void setComments(Set<Comment> comments) {
    this.comments = comments;
  }

}
