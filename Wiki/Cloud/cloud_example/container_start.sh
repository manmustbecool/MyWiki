#!/bin/bash

# compile the Java class
/usr/lib/jvm/java-1.8-openjdk/bin/javac -cp /usr/share/py4j/* AddPy.java

# run the class
java -cp .:/usr/share/py4j/* AddPy 2>&1 &

sleep 2
python py_app.py
