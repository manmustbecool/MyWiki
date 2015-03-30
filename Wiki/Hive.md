---
output:
  html_document:
    highlight: zenburn
    theme: united
---
# Quick installation #

**Assuming that the Hadoop is installed**
```bash
export HADOOP_HOME=/home/ubuntu/hadoop-0.20.205.0
```

**Download the Hive**
```bash
sudo wget http://ftp.heanet.ie/mirrors/www.apache.org/dist/hive/hive-0.9.0/hive-0.9.0.tar.gz
tar a€“xzvf hive-0.9.0.tar.gz
```


**Set the variables**

Edit .bash\_profile to add entry for $HIVE\_HOME and change $PATH.
`vim ~/.bash_profile`

```bash
export HIVE_HOME=/home/ubuntu/hive-0.9.0
export PATH=$PATH:$HIVE_HOME/bin
```
Logout and login for the changes to take effect.
After successful login, typing echo $HIVE\_HOME to check

**Create a space in HDFS for Hive**

```bash
$HADOOP_HOME/bin/start-all.sh // start the Hadoop

$HADOOP_HOME/bin/hadoop fs -mkdir /tmp
$HADOOP_HOME/bin/hadoop fs -mkdir /user/hive/warehouse
$HADOOP_HOME/bin/hadoop fs -chmod g+w /tmp
$HADOOP_HOME/bin/hadoop fs -chmod g+w /user/hive/warehouse
```

Start the Hive CLI

```bash
$HIVE_HOME/bin/hive
```

# Web interface #

Start the web application

```bash
$HIVE_HOME/bin/hive --service hwi
```
To access Hive using the web interface (default port: 9999) - `<Hive Server Address>:9999/hwi`

Creating a `hive-site.xml` in the `hive/con` folder, if it can not find the hwi.war file

```xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
  <property>
    <name>hive.hwi.war.file</name>
    <value>lib/hive-hwi-0.9.0.war</value>
    <description>This sets the path to the HWI war file, relative to ${HIVE_HOME}. </description>
  </property>
</configuration>
```

# JDBC connection #

To interact with Hive using JDBC, first run the Hive Thrift Server (default port: 10000) -

```bash
hive --service hiveserver
# or 
hive --service hiveserver2
```