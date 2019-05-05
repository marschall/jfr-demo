package com.github.marschall.jfrdemo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import javax.management.JMException;

public class Main {

  public static void main(String[] args) throws JMException {
    System.out.println("started");
    Sample.install();
    Runnable[] jobs = new Runnable[] {
        new Sleeper(),
        new FileReader(),
        new FileWriter(),
        new ExceptionRaiser(),
        new ThreadStarter(),
        new MemoryChurner()
    };
    while (!Thread.currentThread().isInterrupted()) {
      for (Runnable job : jobs) {
        job.run();
      }
    }
  }

  static final class Sleeper implements Runnable {

    @Override
    public void run() {
      try {
        Thread.sleep(100L);
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

  static final class FileWriter implements Runnable {

    private final Path path;

    FileWriter() {
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

  static final class ExceptionRaiser implements Runnable {

    private static final int MAX_ROUND = 3;

    private int round;

    private final int[] array;

    private final Object o;

    private final String s;

    ExceptionRaiser() {
      this.round = 0;
      this.array = new int[0];
      this.o = null;
      this.s = "";
    }

    @Override
    public void run() {
      boolean thrown = false;
      try {
        raiseException();
      } catch (RuntimeException e) {
        thrown = true;
        incrementRound();
      }
      if (!thrown) {
        throw new IllegalStateException("no exception thrown");
      }
    }

    private void incrementRound() {
      this.round += 1;
      if (this.round > MAX_ROUND) {
        this.round = 0;
      }
    }

    private void raiseException() {
      switch (this.round) {
      case 0:
        raiseIndexOutOfBoundsException();
        break;
      case 1:
        raiseNullPointerException();
        break;
      case 2:
        raiseStringIndexOutOfBoundsException();
        break;
      case 3:
        raiseIllegalArgumentException();
        break;
      default:
        throw new IllegalStateException("unknown round: " + this.round);
      }
    }

    private void raiseIndexOutOfBoundsException() {
      System.out.println(this.array[0]);
    }

    private void raiseNullPointerException() {
      System.out.println(this.o.toString());
    }

    private void raiseStringIndexOutOfBoundsException() {
      System.out.println(this.s.charAt(0));
    }

    private void raiseIllegalArgumentException() {
      throw new IllegalArgumentException("illegal argument");
    }

  }

  static final class ThreadStarter implements Runnable {

    private static final int THREADS_TO_START = 5;

    private long threadCount;

    private final Object lock;

    private int counter;

    ThreadStarter() {
      this.threadCount = 0L;
      this.lock = new Object();
    }

    void incrementCount() {
      synchronized (this.lock) {
        this.counter += 1;
      }
    }

    int getCount() {
      synchronized (this.lock) {
        return this.counter;
      }
    }

    @Override
    public void run() {
      CyclicBarrier barrier = new CyclicBarrier(THREADS_TO_START);
      Thread[] threads = new Thread[THREADS_TO_START];
      for (int i = 0; i < THREADS_TO_START; i++) {
        Thread thread = new Thread(() -> {
          try {
            barrier.await();
          } catch (InterruptedException e) {
            e.printStackTrace(System.err);
            return;
          } catch (BrokenBarrierException e) {
            e.printStackTrace(System.err);
            return;
          }
          incrementCount();
        }, "runner thread: " + this.threadCount++ + "/" + this.getCount());
        threads[i] = thread;
      }
      for (Thread thread : threads) {
        thread.start();
      }
      for (Thread thread : threads) {
        try {
          thread.join();
        } catch (InterruptedException e) {
          throw new RuntimeException("interrupted", e);
        }
      }
    }

  }

  static final class MemoryChurner implements Runnable {

    private Object[] arrays;
    private int run;

    MemoryChurner() {
      this.arrays = new Object[1024 * 1024];
      this.run = 1;
    }

    @Override
    public void run() {
      if (this.run > 6) {
        // never have a live set of more than 800m
        this.run = 1;
      }
      // clear the entire array
      Arrays.fill(this.arrays, null);
      int upperBound = 1024 * 128 * this.run;
      for (int i = 0; i < upperBound; i++) {
        // allocate 1 kb
        this.arrays[i] = new byte[1024];
      }

      this.run += 1;
    }

  }

}
