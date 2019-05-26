package com.github.marschall.jfr.demo.web.entity;

import static javax.persistence.GenerationType.SEQUENCE;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Comment {

  @Id
  @GeneratedValue(
      strategy = SEQUENCE,
      generator = "comment-sequence-generator"
  )
  @SequenceGenerator(
      name = "comment-sequence-generator",
      sequenceName = "COMMENT_ID"
  )
  private Integer id;

  @Column
  private String comment;

  @Column(name =  "PUBLISHED_AT")
  private OffsetDateTime publishedAt;

  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getComment() {
    return this.comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public OffsetDateTime getPublishedAt() {
    return this.publishedAt;
  }

  public void setPublishedAt(OffsetDateTime publishedAt) {
    this.publishedAt = publishedAt;
  }

}
