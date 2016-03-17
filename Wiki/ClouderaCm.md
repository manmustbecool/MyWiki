

## Trouble shooting

**problem : Installing Cloudera on a fresh Ubuntu and adding a host. error: Could not connect to host.**

_fix :_ It is possible because of the SSH is not installed.

```bash
sudo apt-get install openssh-client openssh-server
```

---

**problem : start HUE by runserver `Error: That port is already in use `**

_fix :_  `http://codeureka.blogspot.ie/2013/07/error-that-port-is-already-in-use.html`

1. Finding process for port 8000

```bash
$ sudo netstat -lpn |grep :8000
# -l : listening ports; -p: display PID; -n : in numeric

tcp        0      0 192.168.1.5:8000    0.0.0.0:*        LISTEN      10797/python   

# here 10797 is a process id running on port 8000.
```

2. Kill the process

```bash
$ kill -9 10797
```


---


**problem : `Datanode denied communication with namenode: DatanodeRegistration(...`**

_fix :_
the namenode IP might is not in the allow list. Common is a case which 2 IP is assigned for a machine e.g. 127.0.0.1; 127.0.1.1

the allowed list is available at:
by command:

```bash
$sudo cat /proc/$(pgrep -f namenode.NameNode)/cwd/dfs_hosts_allow.txt 
```

or UI:

```bash
via Cloudera Manager: All Services > hdfs1 > Instances > namenode (Active) > Processes > Configuration Files/Environment > Show > dfs_hosts_allow.txt 
```

Append the datanode IP in the CM namenode configuration > advance > dfs\_hosts\_allow.txt


---

**problem : Hive `SemanticException Unable to fetch table correlation`**

_fix :_
Run the jps command, look for "RunJar" process and kill it using

```bash
kill -9 {{{<RunJarprocessId>}}} command
```


---


## Updating Cloudera manager


********* reset the cloudera manager password *****************

```bash
# using the password found in:
cat /var/lib/cloudera-scm-server-db/data/generated_password.txt 

# connect psql with root user - cloudera-scm
psql -U cloudera-scm -p 7432 -h localhost -d scm

# for version before 4.8.2:
update USERS set password_hash='ffa2eb4251b38e069e968890cb2bcdb6229982322f5ed2470bf96231fe4c39c8', password_salt=-4382599614486590865 WHERE user_name = 'admin';

# for newer version:
| USER_ID | USER_NAME | PASSWORD_HASH                                                    | PASSWORD_SALT        | PASSWORD_LOGIN | OPTIMISTIC_LOCK_VERSION |
|       1 | admin     | 28b017aa508f8e526eeaa29cce2a909cbe7eb662d3d89a14d68cf4aabfeb159e |  7823478305617615357 |              1 |                       3 |


# show database size:
select t1.datname AS db_name, pg_size_pretty(pg_database_size(t1.datname)) as db_size
  from pg_database t1
  order by pg_database_size(t1.datname) desc;

    db_name  | db_size
-----------+---------
 amon      | 6897 MB
 smon      | 5491 MB
 hmon      | 2003 MB

 amon -> activity monitor
 smon -> serivce monitor
 hmon -> host monitor

  
# list all users:
\du

```

******** stop the Hadoop cluster **************


must stop all Hadoop services  (Hive, impala, etc which are using the database)
and must stop Cloudera Management Services monitor services

if downgrade from 4.8 to 4.7. go back to delete impala service first


********** stop all cloudera manager services ********************
```bash
# list running cloudera services and stop them
$ service --status-all 2>&1 | grep cloudera

sudo service cloudera-scm-server stop
sudo service cloudera-scm-server-db stop
sudo service cloudera-scm-agent stop

----- 

# debain list installed cloudera packages
$ dpkg -l | grep cloudera
# redhat 
$ rpm -qa | grep cloudera


sudo apt-get install cloudera-manager-server cloudera-manager-agent cloudera-manager-daemons

sudo apt-get install cloudera-manager-agent=4.7.2-1.cm472.p0.135~squeeze-cm4.7.2

# check the versions in repo
$ apt-cache show cloudera-manager-agent


-- install from local file
# for debain: 
sudo dpkg -i cloudera-manager-agent_*
sudo dpkg -i cloudera-manager-daemons_* 
sudo dpkg -i cloudera-manager-server-db_*
sudo dpkg -i cloudera-manager-server_*

# for redhat
Install a package
rpm –ivh packagename
upgrade a package
rpm –Uvh packagename 

```

********** restart the services ***********

```bash
sudo service cloudera-scm-server-db start
sudo service cloudera-scm-server start
sudo service cloudera-scm-agent start

```


## Impala performance tuning 

`http://php.sabscape.com/blog/?p=598`

use impala query manager port:7180