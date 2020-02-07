.SUFFIXES: .java .class
.java.class:
	javac $*.java

CLASSES = \
	  main.java \
	  Measurement.java \
	  Node.java \
	  State.java \
	  Action.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
