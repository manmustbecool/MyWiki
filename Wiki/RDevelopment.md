# R Development



## Call R from Java ##

 * **RCaller** 
 
http://www.mhsatman.com/rcaller.php

 * **Rserve** 
 
An R server listens and processes incoming requests of clients. It passes commands to R which were sent by clients.

 * **JRI**
 
Java/R Interface

It use uses the JNI and is part of rJava. http://www.rforge.net/JRI/

Install rJava in R `install.packages("rJava")`

JRI can be found be the R library directory `C:\Program Files\R\R-2.15.0\library\rJava\jri `

Run example in the Windows CMD. (i386 for 32bit)
``` bash
set R_HOME= C:\Program Files\R\R-2.15.0
# The environment variable R_HOME has to be set globally for the system, not only for the current user (restart of the system!) 
set path= %PATH%;C:\Program Files\R\R-2.15.0\bin\i386;C:\Program Files\R\R-2.15.0\library\rJava\jri
cd C:\Program Files\R\R-2.15.0\library\rJava\jri\
java -cp JRI.jar;examples rtest
```

Development in Eclipse Java project

``` bash
 build path > add external class folder : C:\Program Files\R\R-2.15.0\library\rJava\jri
```

In Linux,
``` bash
# sudo R CMD javareconf

sudo apt-get install r-cran-rjava

export JAVA_HOME=/usr/lib/jvm/java-6-sun
export R_HOME=/usr/lib/R
# export PATH=$PATH:/usr/lib/R/site-library/rJava/jri

cd /usr/lib/R/site-library/rJava/jri
java -cp JRI.jar:examples rtest

# sudo cp /usr/lib/R/site-library/rJava/jri libjri.so  /usr/lib
```

JRI sample:

``` java
import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
public class RTest {
 public static void main(String[] args) {
  Rengine engine = new Rengine(new String[]{"--no-save"}, false, null);
  engine.assign("a", new int[]{81});
  REXP result = engine.eval("sqrt(a)");
  System.out.println(result.asDouble());
  engine.end();
 }
}
```

 * **Renjin** 
 
 A new JVM-based R language interpreter.
 http://code.google.com/p/renjin/ http://stdioe.blogspot.ie/2011/09/embedding-r-in-java-applications-using.html


## Call Java from R ##
  * **rJava**
  
http://www.rforge.net/rJava/


## Other ##

 * past project
 
 [RPig: Concise Programming Framework by Integrating R with Pig for Big Data Analytics](../papers/Rpig%20Concise%20Programming%20Framework%20by%20Integrating%20R%20with%20Pig%20for%20Big%20Data%20Analytics%20-%20book%20chapter%20final.pdf)


