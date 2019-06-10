package com.github.marschall.jfr.demo.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.github.marschall.jfr.demo.web.controller.AuthorController;
import com.github.marschall.jfr.demo.web.service.AuthorService;

@EnableWebMvc
public class DispatcherConfiguration implements WebMvcConfigurer {

  @Autowired
  private AuthorService autorService;

  @Bean
  public AuthorController jpaController() {
    return new AuthorController(this.autorService);
  }

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    registry.freeMarker();
  }

  @Bean
  public FreeMarkerConfigurer freemarkerConfigurer() {
    FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
    freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/");
    return freeMarkerConfigurer;
  }

  @Bean
  public FreeMarkerViewResolver freemarkerViewResolver() {
    FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
    resolver.setCache(true);
    resolver.setPrefix("");
    resolver.setSuffix(".ftl");
    resolver.setCacheLimit(16);
    return resolver;
  }

}
