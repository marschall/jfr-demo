package com.github.marschall.jfr.demo.web.model;

public class AuthorsViewModelEntry {

  private final String authorName;

  private final int articleCount;

  private final int commentCount;

  public AuthorsViewModelEntry(String authorName, int articleCount, int commentCount) {
    this.authorName = authorName;
    this.articleCount = articleCount;
    this.commentCount = commentCount;
  }

  public String getAuthorName() {
    return this.authorName;
  }

  public int getArticleCount() {
    return this.articleCount;
  }

  public int getCommentCount() {
    return this.commentCount;
  }

}
