package com.github.marschall.jfr.demo.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.OffsetTime;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DemoAsyncServlet extends HttpServlet {

  private static final TimeAttachment TIME_ATTACHMENT = new TimeAttachment();

  private static final String ATTACHEMENT = "com.github.marschall.jfr.demo.web.attachment";

  private volatile ScheduledExecutorService exexutor;

  @Override
  public void init() throws ServletException {
    this.exexutor = Executors.newScheduledThreadPool(1);
  }

  @Override
  public void destroy() {
    this.exexutor.shutdown();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Attachment attachment;
    Object attribute = req.getAttribute(ATTACHEMENT);
    if (attribute instanceof Attachment) {
      attachment = (Attachment) attribute;
    } else {
      resp.setContentType("text/plain");
      attachment = new CompositeAttachement(List.of(TIME_ATTACHMENT, new CountDownAttachment(this.exexutor)));
      req.setAttribute(ATTACHEMENT, attachment);
    }
    attachment.execute(req, resp);
  }



  static final class CompositeAttachement implements Attachment {

    private final List<Attachment> attachements;

    CompositeAttachement(List<Attachment> attachements) {
      this.attachements = attachements;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      for (Attachment attachment : this.attachements) {
        attachment.execute(req, resp);
      }
    }

  }

  static final class CountDownAttachment implements Attachment {

    private final AtomicInteger runs = new AtomicInteger(10);

    private final ScheduledExecutorService exexutor;

    CountDownAttachment(ScheduledExecutorService exexutor) {
      this.exexutor = exexutor;
    }

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
      int current = this.runs.decrementAndGet();
      if (current > 0) {
        AsyncContext asyncContext;
        if (req.isAsyncStarted()) {
          asyncContext = req.getAsyncContext();
        } else {
          asyncContext = req.startAsync();
        }
        this.exexutor.schedule((Runnable) asyncContext::dispatch, 1, TimeUnit.SECONDS);
      }
    }
  }

  static final class TimeAttachment implements Attachment {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      PrintWriter writer = resp.getWriter();
      writer.append(OffsetTime.now().toString());
      writer.append("\r\n");
      resp.flushBuffer();
    }

  }

  @FunctionalInterface
  interface Attachment {

    void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

  }

}
