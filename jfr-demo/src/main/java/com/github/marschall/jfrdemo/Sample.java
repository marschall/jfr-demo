package com.github.marschall.jfrdemo;

import java.lang.management.ManagementFactory;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

public class Sample implements SampleMXBean {

  private static final String OBJECT_NAME = "com.github.marschall.jfrdemo:type=Sample";

  @Override
  public int getValue() {
    long value = ((System.currentTimeMillis() / 1_000L) % 21L) - 10L;
    return (int) value;
  }


  static void install() throws JMException {
    MBeanServer server = ManagementFactory.getPlatformMBeanServer();
    ObjectName mxBeanName = new ObjectName(OBJECT_NAME);
    Sample mxBean = new Sample();
    server.registerMBean(mxBean, mxBeanName);
  }

}
