package com.github.marschall.jfr.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JpaController {

  @GetMapping("/")
  public String greeting(Model model) {
//    model.addAttribute("name", name);
    return "greeting";
  }

}
