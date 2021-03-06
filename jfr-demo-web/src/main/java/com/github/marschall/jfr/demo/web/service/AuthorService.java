package com.github.marschall.jfr.demo.web.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.marschall.jfr.demo.web.model.AuthorsViewModel;
import com.github.marschall.jfr.demo.web.repository.AuthorRepository;

@Service
public class AuthorService {

  private final AuthorRepository repository;

  public AuthorService(AuthorRepository repository) {
    this.repository = repository;
  }

  @Transactional(readOnly = true)
  public AuthorsViewModel loadModel() {
    return this.repository.loadModel();
  }

}
