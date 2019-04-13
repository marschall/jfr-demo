package com.github.marschall.jfrdemo;

import org.junit.jupiter.api.Test;

import com.github.marschall.junit.jfr.JfrProfiled;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Event;
import jdk.jfr.Label;

@JfrProfiled
class CustomEventTests {

  @Test
  void emitCustomEvent() throws InterruptedException {
    var demoEvent = new DemoEvent();
    demoEvent.setMessage("Hello JFR");

    demoEvent.begin();
    try {
      Thread.sleep(10_000L);
    } finally {
      demoEvent.end();
      demoEvent.commit();
    }

  }

  @Category("Demo")
  @Label("Demo Event")
  @Description("A simple demo event")
  static final class DemoEvent extends Event {


    @Label("groupId")
    @Description("The groupId of the executed project")
    private String message;


    String getMessage() {
      return this.message;
    }

    void setMessage(String groupId) {
      this.message = groupId;
    }

  }

}
