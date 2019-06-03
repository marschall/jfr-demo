package com.github.marschall.jfr.demo.ejb;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EjbDemoServlet extends HttpServlet {

  @EJB
  private SampleEjb sampleEjb;

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    resp.setContentType("text/plain");
    PrintWriter writer = resp.getWriter();
    for (Integer i : this.sampleEjb.getRange()) {
      writer.println(i);
    }
  }

}
