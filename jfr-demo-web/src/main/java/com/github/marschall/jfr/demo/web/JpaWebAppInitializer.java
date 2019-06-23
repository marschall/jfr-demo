package com.github.marschall.jfr.demo.web;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.github.marschall.jfr.demo.web.configuration.ApplicationConfiguration;
import com.github.marschall.jfr.demo.web.configuration.DispatcherConfiguration;
import com.github.marschall.jfr.demo.web.configuration.H2Configuration;
import com.github.marschall.jfr.demo.web.configuration.HibernateConfiguration;
import com.github.marschall.jfr.demo.web.configuration.JdbcRepositoryConfiguration;
import com.github.marschall.jfr.demo.web.configuration.JdbcTemplateConfiguration;
import com.github.marschall.jfr.demo.web.configuration.JpaRepositoryConfiguration;

public class JpaWebAppInitializer implements WebApplicationInitializer {

  private static final AtomicInteger SERVLET_COUNT = new AtomicInteger();

  @Override
  public void onStartup(ServletContext container) {

    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
    rootContext.register(H2Configuration.class);

    // Manage the lifecycle of the root application context
    container.addListener(new ContextLoaderListener(rootContext));

    registerController(container, HibernateConfiguration.class, JpaRepositoryConfiguration.class, "/jpa/*");
    registerController(container, JdbcTemplateConfiguration.class, JdbcRepositoryConfiguration.class, "/jdbc/*");
  }

  private static void registerController(ServletContext container,
          Class<?> persistenceConfiguration, Class<?> repositoryConfiguration,
          String urlPattern) {

    // Create the dispatcher servlet's Spring application context
    AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(ApplicationConfiguration.class);
    dispatcherContext.register(persistenceConfiguration);
    dispatcherContext.register(repositoryConfiguration);
    dispatcherContext.register(DispatcherConfiguration.class);

    Dynamic dispatcher = container.addServlet("dispatcher-" + SERVLET_COUNT.incrementAndGet(), new DispatcherServlet(dispatcherContext));
    dispatcher.addMapping(urlPattern);

  }

}