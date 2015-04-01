Apache Mahout

# Development #

## Clustering ##

  1. Vectorization
  * An implementation of `Vector` is instantiated and filled in for each object.
  * all Vectors are written to a file in the SequenceFile format (from the Hadoop library), which is read by the Mahout algorithms.


## Write a reader for Mahout output file ##

```java
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.kmeans.Cluster;

...
	
	Configuration conf = new Configuration();
	FileSystem fs = FileSystem.get(conf);

	SequenceFile.Reader reader = new SequenceFile.Reader(fs, new Path(
				"output/clusters-2/part-r-00000"), conf);

	Text text = new Text();
	Cluster cluster = new Cluster();

	while (reader.next(text, cluster)) {
		System.out.println(text + "   " + cluster);
	}
	reader.close();
```

In above case, each row of reading is (text, cluster). When we open the raw Hadoop output file with a text editor, we will see the _text_ and _cluster_ at the head of the file.

```bash
SEQorg.apache.hadoop.io.Text+org.apache.mahout.clustering.kmeans.Cluster...
```

An example of another case:
```java
SEQorg.apache.hadoop.io.Text%org.apache.mahout.math.VectorWritable...
```

# Other #

## Install the Apache Maven plug-in for the Eclipse ##

The Mahout project uses the Apache Maven build and release system.

There are many Maven plug-ins. We need install the correct one:

m2e - Maven Integration for Eclipse
http://eclipse.org/m2e/

eclipse install Url:
http://download.eclipse.org/technology/m2e/releases/


## Checkout the laterest Mahout source code ##

Within Eclipse, we need install the Subclipse

**Install the Subclipse for the Eclipse**

Eclipse install Url:
http://subclipse.tigris.org/update_1.8.x

#### Problem: proxy server configuation ####

```bash
RA layer request failed
```

Configuring proxy setting of Eclipse preferences will not solve this problem.

We have to edit the file named "servers" that is stored in the Subversion runtime configuration area.

On Windows:
Open the Run dialog and enter %APPDATA%\Subversion and click OK.

On Linux:
$ cd ~/.subversion/
$ vim servers

Uncomment `http-proxy-host` and `http-proxy-port` under the `[global]` section.

Making sure that the edited lines are without any space:

```bash
http-proxy-host = www-proxy.xxxx.se
http-proxy-port = 8080
```

to

```bash
http-proxy-host=www-proxy.xxxx.se
http-proxy-port=8080
```

Otherwise, it gives a error message:

```bash
Malformed file
svn: C:\Users\xxxxxx\AppData\Roaming\Subversion\servers:144: Option expected
```

**Checkout the laterest Mahout source code**

SVN url: http://svn.apache.org/repos/asf/mahout/trunk