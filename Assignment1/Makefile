JCC = javac
JFLAGS = -g

default: Radar.class Correlate.class Max.class

Radar.class: Radar.java
	$(JCC) $(JFLAGS) Radar.java

Correlate.class: Correlate.java
	$(JCC) $(JFLAGS) Correlate.java

Max.class: Max.java
	$(JCC) $(JFLAGS) Max.java

clean:
	$(RM) *.class