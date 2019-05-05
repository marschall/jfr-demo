package com.github.marschall.jfrdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

  public static void main(String[] args) {
    Runnable[] jobs = new Runnable[] {
        new Sleeper(),
        new FileReader(),
        new WriterReader()
    };
    while (true) {
      for (Runnable job : jobs) {
        job.run();
      }
    }
  }

  static final class Sleeper implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(1L);
      } catch (InterruptedException e) {
        throw new RuntimeException("interrupted", e);
      }
    }

  }

  static final class FileReader implements Runnable {

    private final Path path;

    FileReader() {
      this.path = Path.of("/dev/urandom");
    }

    @Override
    public void run() {
      try (InputStream stream = Files.newInputStream(path)) {
        int b = stream.read();
        if (b == -1) {
          System.out.println(this.path + " is empty");
        }
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }

    }

  }

  static final class WriterReader implements Runnable {

    private final Path path;

    WriterReader() {
      this.path = Path.of("/dev/null");
    }

    @Override
    public void run() {
      try (OutputStream stream = Files.newOutputStream(path, StandardOpenOption.WRITE)) {
        stream.write(42);
      } catch (IOException e) {
        throw new UncheckedIOException(e);
      }

    }

  }

}
