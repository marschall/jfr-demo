package com.github.marschall.jfrdemo;

public class Main {

  public static void main(String[] args) {
    while (true) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {
        return;
      }
    }
  }

}
