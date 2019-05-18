package com.github.marschall.jfr.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoAsyncServlet extends HttpServlet {

  private static final String ATTACHEMENT = "com.github.marschall.jfr.demo.web.attachment";

  private volatile ScheduledExecutorService exexutor;

  @Override
  public void init() throws ServletException {
    this.exexutor = Executors.newScheduledThreadPool(1);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (!req.isAsyncStarted()) {
      resp.setContentType("text/plain");
      AsyncContext asyncContext = req.startAsync(req, resp);
      CountDownRunnable countDownRunnable = new CountDownRunnable(this.exexutor, asyncContext);
      countDownRunnable.schedule();
    } else {
      Object attribute = req.getAttribute(ATTACHEMENT);
      if (attribute instanceof Attachment) {
        Attachment attachment = (Attachment) attribute;
        attachment.execute(req, resp);
      }
    }
  }

  static final class CountDownRunnable implements Runnable {

    private int runs;
    private final ScheduledExecutorService exexutor;
    private final AsyncContext asyncContext;

    CountDownRunnable(ScheduledExecutorService exexutor, AsyncContext asyncContext) {
      this.exexutor = exexutor;
      this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
      this.asyncContext.dispatch();
      if (this.runs < 10) {
        this.schedule();
      } else {
        this.asyncContext.complete();
      }
    }

    void schedule() {
      this.exexutor.schedule(this, 1, TimeUnit.SECONDS);
    }

  }

  static final class TimeAttachment implements Attachment {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      PrintWriter writer = resp.getWriter();
      writer.append(OffsetDateTime.now().toString());
      resp.flushBuffer();
    }

  }

  @FunctionalInterface
  interface Attachment {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

  }

}
