# JFR Demo

A demonstration of using projects that generate JFR events.

## Running the Included Demos


set the Maven options

```
export MAVEN_OPTS="-XX:StartFlightRecording:filename=recording.jfr,settings=$(pwd)/src/main/resources/Zulu_Profiling.jfc -XX:FlightRecorderOptions:stackdepth=256"
```

run the Maven demo

```
mvn -pl jfr-demo -am clean test
```

run the Web demo http://127.0.0.1:8080/

```
mvn -pl jfr-demo-web -am jetty:run
```

dumping

```
jcmd 99776 JFR.dump name=1
```


## Running JFR in General

create a recording

```
jcmd 19993 JFR.configure stackdepth=256
jcmd 19993 JFR.start name=\"Zulu Recording\" settings=${HOME}/git/jfr-demo/src/main/resources/Zulu_Profiling.jfc duration=2m filename=${HOME}/git/jfr-demo/src/main/resources/Zulu_Recording.jfr
jcmd 19993 JFR.stop name=\"Zulu Recording\" filename=${HOME}/git/jfr-demo/src/main/resources/Zulu_Recording2.jfr
```

print perf counters

```
jcmd 19993 PerfCounter.print
```

