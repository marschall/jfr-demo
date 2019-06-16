package com.github.marschall.jfrdemo.configuration;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.github.marschall.jfr.jdbc.JfrDataSource;

@Configuration
public class H2Configuration {

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabase embeddedDatabase = new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.H2)
      .addScript("classpath:h2_schema.sql")
      .addScript("classpath:h2_data.sql")
      .build();
    return new JfrDataSource(embeddedDatabase);
  }

}
