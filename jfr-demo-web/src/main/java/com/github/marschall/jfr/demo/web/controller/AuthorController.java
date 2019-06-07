package com.github.marschall.jfr.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.github.marschall.jfr.demo.web.model.AuthorsViewModel;
import com.github.marschall.jfr.demo.web.service.AuthorService;

@Controller
public class AuthorController {

  private final AuthorService autorService;

  public AuthorController(AuthorService autorService) {
    this.autorService = autorService;
  }

  @GetMapping("/")
  public String greeting(Model model) {
    AuthorsViewModel authorModel = this.autorService.loadModel();
    model.addAttribute("authorModel", authorModel);
    return "authors";
  }

}
