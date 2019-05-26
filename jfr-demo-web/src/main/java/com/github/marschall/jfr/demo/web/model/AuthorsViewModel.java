package com.github.marschall.jfr.demo.web.model;

import java.util.List;

public final class AuthorsViewModel {

  private final List<AuthorsViewModelEntry> entries;

  public AuthorsViewModel(List<AuthorsViewModelEntry> entries) {
    this.entries = entries;
  }

  public List<AuthorsViewModelEntry> getEntries() {
    return this.entries;
  }

}
