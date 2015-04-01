---

---

# Setup quick guide #

## setup a first master node ##

  * Installing Java and SSH

```bash
# For Ubuntu 11
sudo apt-add-repository ppa:flexiondotorg/java
sudo apt-get update
sudo apt-get install sun-java6-jdk

# if installation is inside of a VM and behined a proxy
# In addition to configuring proxies, tell sudo to consider the environment with the flag -E
export http_proxy=http://<proxy>:<port>
export https_proxy=http://<proxy>:<port>
sudo -E apt-add-repository ppa:flexiondotorg/java

```

Check if java is installed `java version`

  * Adding a ubuntu user if necessary. For example, a new user(ubuntu) in a group(hadoop) :

```bash
sudo addgroup hadoop
sudo adduser -ingroup hadoop ubuntu
```

We use the default ubuntu:ubuntu user from Amazon EC2 instance in the following.

  * Setup passphraseless ssh

```bash
su - ubuntu
ssh-keygen -t rsa -P ""

cat $HOME/.ssh/id_rsa.pub >> $HOME/.ssh/authorized_keys
```

  * Download hadoop-0.20.2.tar.gz,Unzip then give chown permissions to user(ubuntu) for all files in the work directory.
  

```bash
wget https://archive.apache.org/dist/hadoop/core/hadoop-0.20.205.0/hadoop-0.20.205.0.tar.gz
sudo tar xzf hadoop-0.20.205.0.tar.gz
sudo chown -R ubuntu hadoop-0.20.205.0
```

  * Creating a file directory (hadoop.tmp.dir) for HDFS.
  
```bash
sudo mkdir -p /home/ubuntu/myhdfs
sudo chown ubuntu:ubuntu /home/ubuntu/myhdfs
```

  * Basic Hadoop configuation
    * Adding the following in the `hadoop-env.sh`  
    
    ```bash
    export JAVA_HOME=/usr/lib/jvm/java-6-sun
    export HADOOP_OPTS=-Djava.net.preferIPv4Stack=true
    ```

    * Adding the following between the `<configuration> ... </configuration>` tags of `inconf/core-site.xml`.
      
    ```xml
    <property>
      <name>hadoop.tmp.dir</name>
      <value>/home/ubuntu/myhdfs</value>
      <!-- For default : mkdir -p /tmp/hadoop-username/dfs -->
    </property> 
    
    <property>
      <name>fs.default.name</name>
      <value>hdfs://localhost:54310</value>
    </property>
    ```
    * Adding in file `conf/mapred-site.xml`:

    ```xml
    <property>
      <name>mapred.job.tracker</name>
      <value>localhost:54311</value>
    </property>
    ```

    * Adding in file `conf/hdfs-site.xml`:
    
    ```xml
    <property>
      <name>dfs.replication</name>
      <value>1</value>
    </property>
    ```

  * Format the HDFS. This is only needed for first time on setup.
  
```bash
hadoop-0.20.205.0/bin/hadoop namenode –format
```

  * Start Hadoop
  
```bash
hadoop-0.20.205.0/bin/start-all.sh
```
Using `bin/hadoop fsck /` to ckeck if all data nodes are on.


## Add a new slave node ##

  * Installing ssh Java6 and Hadoop on the new slave node.

  * Adding a hadoop user if necessary.

Copying the master's key to the salve, on the master node types follows

```bash
ssh-copy-id -i $HOME/.ssh/id_rsa.pub ubuntu@159.xxx.xxx.xxx
```

Following configuation with files which are all from the `hadoop_home/conf`

  * Download and unzip the Hadoop.

  * Hadoop configuation
    * Do the same in file `hadoop-env.sh` as we did for the master node.
    * Copying the configuations of `core-site.xml`, `hdfs-site.xml` and `mapred-site.xml` from the master to the slave node. In this case, there is no custom settings on the new node. The `localhost` should be replaced with IP of the master node.

  * Editing the `Slaves` file of the master node. Appending the IP address or hostname of the slave node at the end of file.
  
```bash
localhost
159.xxx.xxx.xxx
```

  * Restarting the master node by `bin/start-all.sh`. After a moment, the node will be initialized and appeared on the Web admin of the master.

## Add and remove a configured slave node ##

  * Add node
    * Firstly, add the node's IP address or hostname to the `Hadoop_Home/conf/slaves` file located on the master node. And making sure that the node is not listed in the exclude file.
    * New node can be started without affecting the running of the existing cluster. Running `/bin/hadoop-daemon.sh start datanode` and `bin/hadoop-daemon.sh start tasktracker` will start the data storage and task tracker processes on the new node, therefore adding it to the cluster.

  * Remove node
    * Firstly, the node must be added to the exclude file. Next, the command `bin/Hadoop dfsadmin –refreshNodes` must be run on the master server. This forces the master to repopulate the list of valid nodes from the slaves and exclude files
    * The master server will begin a decommissioning process on that node. Decommissioning a node is meant to prevent data loss and lasts until all blocks on the node are replicated.

## Troubleshooting ##

**DFS Format** 

The command for DFS format

```bash
bin/hadoop namenode -format
```

**Problem: Format aborted** 

```bash
Format aborted in /home/hduser/hadooptmp/dfs/name
11/10/25 04:29:40 INFO namenode.NameNode: SHUTDOWN_MSG: 
/************************************************************
SHUTDOWN_MSG: Shutting down NameNode at ubuntu/127.0.1.1
```
If the name node is already shutdown, then go to the dfs directory and manually delete all files. After this, input the format command again.

**Problem: safe mode** 

Name node stucks in safe mode
`org.apache.hadoop.dfs.SafeModeException`
Use the following command to turn off the safe mode.

```bash
bin/hadoop dfsadmin -safemode leave
```


# Development #

## Development with Eclipse plugin ##

With many hadoop tutorials I had following problems with Hadoop Eclipse plugin.

**Problem: failed on connection** 

The possible error messages:

```bash
Error:null

Error: Call to localhost/127.0.0.1:54310 failed on local exception: java.io.EOFException

Error: Call to localhost/127.0.0.1:54310 failed on connection exception: 
java.net.ConnectException: Connection Refused. 
```

_Fix_:

Just install the **Cygwin**, then adding the cygwin path to the `Path` of Environment Variables: `;c:\cygwin\bin;c:\cygwin\usr\bin`. Restart the eclipse, the problem will be fixed.


**Problem: Permission denied** 

```bash
Permission denied: user=xxx\xxxxx, access=WRITE, inode="":hduser:supergroup:rwxr-xr-
```

_Fix_:

Changing the permission. This Must be done in the dfs system
```
hadoop fs -chmod -R ugo+rwx /user 
```


# Hadoop tutorial #

VM image hadoop-appliance-0.18.0.vmx
http://developer.yahoo.com/hadoop/tutorial/index.html

http://v-lad.org/Tutorials/Hadoop/00%20-%20Intro.html

