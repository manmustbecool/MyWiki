---
output:
  html_document:
    fig_caption: yes
    highlight: zenburn
    keep_md: yes
    number_sections: yes
    theme: spacelab
    toc: yes
---

**problem : Installing Cloudera on a fresh Ubuntu and adding a host. error: Could not connect to host.**

_fix :_ It is possible because of the SSH is not installed.
```
sudo apt-get install openssh-client openssh-server
```

---


**problem : hdfs healthy, percentage under replicated blocks is too high**

_fix :_ execute fsck commend the under replicated blocks
```
hdfs fsck /user/hive/ -blocks
```

HDFS will automatically fix the under replicated blocks in the background.

If you really want to speed things up, you can run hadoop `dfs -setrep` on the affected files to increase the replication factor and then decrease it back to the default.


---


**problem : start HUE by runserver `Error: That port is already in use `**

_fix :_  `http://codeureka.blogspot.ie/2013/07/error-that-port-is-already-in-use.html`

1. Finding process for port 8000

```
$ sudo netstat -lpn |grep :8000
# -l : listening ports; -p: display PID; -n : in numeric

tcp        0      0 192.168.1.5:8000    0.0.0.0:*        LISTEN      10797/python   

here 10797 is a process id running on port 8000.
```

2. Kill the process

```
$ kill -9 10797
```


---


**problem : `Datanode denied communication with namenode: DatanodeRegistration(...`**

_fix :_
the namenode IP might is not in the allow list. Common is a case which 2 IP is assigned for a machine e.g. 127.0.0.1; 127.0.1.1

the allowed list is available at:
by command:
```
$sudo cat /proc/$(pgrep -f namenode.NameNode)/cwd/dfs_hosts_allow.txt 
```

or UI:
```
via Cloudera Manager: All Services > hdfs1 > Instances > namenode (Active) > Processes > Configuration Files/Environment > Show > dfs_hosts_allow.txt 
```

Append the datanode IP in the CM namenode configuration > advance > dfs\_hosts\_allow.txt


---


**problem : Hive
`SemanticException Unable to fetch table correlation`**

_fix :_
Run the jps command, look for "RunJar" process and kill it using
```
kill -9 {{{<RunJarprocessId>}}} command
```


---


### Impala performance tuning 

`http://php.sabscape.com/blog/?p=598`

use impala query manager port:7180