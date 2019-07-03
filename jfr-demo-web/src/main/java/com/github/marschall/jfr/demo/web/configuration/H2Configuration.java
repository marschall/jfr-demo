package com.github.marschall.jfr.demo.web.configuration;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.github.marschall.jfr.jdbc.JfrDataSource;

@Configuration
public class H2Configuration {

  @Bean
  public DataSource dataSource() {
    String databaseUrl = "jdbc:h2:file:" + getDatabasePath();
    var dataSource = new SingleConnectionDataSource(databaseUrl, true);
    try (var connection = dataSource.getConnection()) {
      boolean changed = false;
      if (!schemaExists(connection)) {
        loadSchema(connection);
        changed = true;
      }
      if (!dataExists(connection)) {
        loadData(connection);
        changed = true;
      }
      if (changed) {
        connection.commit();
      }
    } catch (SQLException | IOException e) {
      throw new BeanCreationException("dataSource", "could not create data source", e);
    }
    return new JfrDataSource(dataSource);
  }

  private static String getDatabasePath() {
    var path = Paths.get("target", "h2").toAbsolutePath();
    try {
      Files.createDirectories(path);
    } catch (IOException e) {
      throw new BeanCreationException("could not create database path", e);
    }
    return path.toString();
  }

  private static boolean schemaExists(Connection connection) throws SQLException {
    try (var preparedStatement = connection.prepareStatement("SELECT COUNT(1)"
            + " FROM INFORMATION_SCHEMA.TABLES"
            + " WHERE TABLE_NAME = ?")) {
      preparedStatement.setString(1, "AUTHOR");
      try (var resultSet = preparedStatement.executeQuery()) {
        resultSet.next();
        return resultSet.getInt(1) == 1;
      }
    }
  }

  private static boolean dataExists(Connection connection) throws SQLException {
    try (var preparedStatement = connection.prepareStatement("SELECT COUNT(1)"
            + " FROM AUTHOR")) {
      try (var resultSet = preparedStatement.executeQuery()) {
        resultSet.next();
        return resultSet.getInt(1) > 0;
      }
    }
  }

  private static void loadFromClassPath(Connection connection, String scriptName) throws SQLException, IOException {
    try (var inputStream = H2Configuration.class.getClassLoader().getResourceAsStream(scriptName);
         var inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
      RunScript.execute(connection, inputStreamReader);
    }
  }

  private static void loadSchema(Connection connection) throws SQLException, IOException {
    loadFromClassPath(connection, "h2-schema.sql");
  }

  private static void loadData(Connection connection) throws SQLException, IOException {
    loadFromClassPath(connection, "h2-data.sql");
  }

}
