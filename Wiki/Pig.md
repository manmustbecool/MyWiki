

# Tutorial #

_Programming Pig_ by Alan F Gates
http://ofps.oreilly.com/titles/9781449302641/index.html

# Development #

**Problem : ERROR 1000: Error during parsing with parameter**

http://stackoverflow.com/questions/3515481/pig-latin-load-multiple-files-from-a-date-range-part-of-the-directory-structur

With an input command `pig -file xxx.pig -param timep=201110271717` has a parameter.

And a pig script line `startTime >= (long)$timep` takes the parameter.

```
org.apache.pig.impl.logicalLayer.FrontendException: ERROR 1000: Error during parsing. For input string: "201110271717"
```

_Fix:_

Adding a quote in the pig script line `mytime >= (long)'$timep'` to avoid pig directly parse the $timep to an integer.


---

**Problem : Unexpected internal error. Failed to create DataStorage**

The hadoop version bundled with Pig is different with the version of the hadoop cluster.

_Fix:_

Set HADOOP\_HOME for pig execution enviroment. E.g.,
```
export HADOOP_HOME=/home/ubuntu/hadoop-0.20.205.0
java -cp pig-0.11.0-SNAPSHOT-withouthadoop.jar:$HADOOP_HOME/hadoop-core-0.20.205.0.jar:$HADOOP_HOME/lib/*:$HADOOP_HOME/conf:...
```


---

**Problem : UDF tasks fail in cluster mode**

```
java.io.IOException: Deserialization error: could not instantiate 'org.apache.pig.scripting....
```

_Fix:_

Use relative path for registering UDF scripts.
```
Register 'test.py' using jython as myfuncs;
```


---

**Problem : unable to explicitly set the schema on TOBAG(...) as bag{tuple(val:double)} - {(NULL)}**

_Fix:_

TOBAG(TOTUPLE(...)) as bag{tuple(val:double)}


---

**Problem : Pig UDFs jar on Amazon EMR**
```
ERROR 2998: Unhandled internal error. org/apache/pig/LoadFunc
```

_Fix:_

Add the pig.jar to the classpath
copy the pig-