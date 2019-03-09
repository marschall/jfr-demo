JFR Demo
========

A demonstration of using projects that generate JFR events.

Create a recording

```
jcmd 19993 JFR.configure stackdepth=256
jcmd 19993 JFR.start name=\"Zulu Recording\" settings=${HOME}/git/jfr-demo/src/main/resources/Zulu_Profiling.jfc duration=2m filename=${HOME}/git/jfr-demo/src/main/resources/Zulu_Recording.jfr
```

Print perf counters

```
jcmd 19993 PerfCounter.print
```

```
-XX:StartFlightRecording:filename=recording.jfr
-XX:FlightRecorderOptions:stackdepth=256
```

