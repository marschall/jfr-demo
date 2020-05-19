package com.github.marschall.jfrdemo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import jdk.jfr.EventType;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

public class RecordingFileDemo {

  public static void main(String[] args) throws IOException {
    Path recordingPath = Paths.get("").toAbsolutePath().getParent().resolve("maven-recording.jfr");
    try (RecordingFile recordingFile = new RecordingFile(recordingPath)) {
      while (recordingFile.hasMoreEvents()) {
        RecordedEvent event = recordingFile.readEvent();
        EventType eventType = event.getEventType();
        if (eventType.getName().equals("com.github.marschall.hibernate.jfr.JfrStatementInspector$JfrStatementEvent")) {
          System.out.println(event.getString("sql"));
        }
      }
    }
  }

}
