JCC = javac
JFLAGS = -g

default: WordApp.class WordRecord.class WordDictionary.class WordPanel.class Score.class

WordApp.class: WordApp.java
	$(JCC) $(JFLAGS) WordApp.java

WordRecord.class: WordRecord.java
	$(JCC) $(JFLAGS) WordRecord.java

WordDictionary.class: WordDictionary.java
	$(JCC) $(JFLAGS) WordDictionary.java

WordPanel.class: WordPanel.java
	$(JCC) $(JFLAGS) WordPanel.java

Score.class: Score.java
	$(JCC) $(JFLAGS) Score.java

clean:
	$(RM) *.class