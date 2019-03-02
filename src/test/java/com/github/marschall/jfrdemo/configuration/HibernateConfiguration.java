package com.github.marschall.jfrdemo.configuration;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@Import(H2Configuration.class)
public class HibernateConfiguration {
  
  @Autowired
  private DataSource dataSource;


  @Bean
  public LocalContainerEntityManagerFactoryBean entityManager() {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setPersistenceUnitName("h2");
    bean.setJpaDialect(new HibernateJpaDialect());
    bean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    bean.setDataSource(dataSource);
    return bean;
  }
  
  @Bean
  public PlatformTransactionManager txManager(EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }

}
