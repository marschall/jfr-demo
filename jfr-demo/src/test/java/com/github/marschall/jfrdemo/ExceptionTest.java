package com.github.marschall.jfrdemo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExceptionTest {

  @Test
  void test1() {
    method1a();
    method1b();
    method1c();
  }
  
  private void method1a() {
    throw1("method1a");
    throw1("method1a");
  }
  
  private void method1b() {
    throw1("method1b");
    throw2();
  }
  
  private void method1c() {
    method1d();
  }
  
  private void method1d() {
    throw1("method1d");
  }
  
  private void throw1(String s) {
    try {
      throw new CustomException(s);
    } catch (RuntimeException e) {
      return;
    }
  }

  @Test
  void test2() {
    throw2();
  }
  
  private void throw2() {
    try {
      throw new CustomException("throw 2");
    } catch (RuntimeException e) {
      return;
    }
  }
  
  static final class CustomException extends RuntimeException {

    CustomException(String message) {
      super(message);
    }
    
  }

}
