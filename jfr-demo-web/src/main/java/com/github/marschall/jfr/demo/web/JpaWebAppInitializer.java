package com.github.marschall.jfr.demo.web;

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
import com.github.marschall.jfr.demo.web.configuration.JpaRepositoryConfiguration;

public class JpaWebAppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext container) {
    // Create the 'root' Spring application context
    AnnotationConfigWebApplicationContext rootContext =
        new AnnotationConfigWebApplicationContext();
    rootContext.register(ApplicationConfiguration.class);
    rootContext.register(H2Configuration.class);
    rootContext.register(HibernateConfiguration.class);
    rootContext.register(JpaRepositoryConfiguration.class);

    // Manage the lifecycle of the root application context
    container.addListener(new ContextLoaderListener(rootContext));

    // Create the dispatcher servlet's Spring application context
    AnnotationConfigWebApplicationContext dispatcherContext =
        new AnnotationConfigWebApplicationContext();
    dispatcherContext.register(DispatcherConfiguration.class);

    // Register and map the dispatcher servlet
    Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
    //      dispatcher.setLoadOnStartup(1);
    dispatcher.addMapping("/jpa");
  }

}