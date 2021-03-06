---
title: "Linux Basic"
---


# Ubuntu #

## user and permission

```bash
# modify permissions
# -R – apply to the parent folder and the child objects within
# ugo - User, Group, and Other
# wrx -  read, write and execute
sudo chmod -R ugo+rwx /DATA/SHARE

# Add user to “sudo” group
adduser <username>
adduser <username> sudo

# list groups
groups

# list groups which a user belong to
groups <username>

# list group members
sudo apt-get install members
members <groupname>

# reset root password 
http://askubuntu.com/questions/24006/how-do-i-reset-a-lost-administrative-password

# list Current Logged In Users 
w # Shows information about the users currently on the machine, and their processes.
who # Shows information about users who are currently logged in.
```
##processes

```bash
# top processes
top
# or
sudo apt-get install htop
htop

# -e: select all processes; -f: does full listing
ps -ef | grep java

# show all processes for all users. -x:all users
ps -aux
# for root user
ps -auroot

# kill the process by id
kill -9 process_id
```
## command
```bash
# push a command into the background. When the terminal session is closed, the command ends.
command &

# also prevent command print messages into the terminal 
command &>/dev/null &

# also keep the commend runing even When the terminal session is closed.
nohup command &>/dev/null &
```

## file

```bash

# rename a folder name
mv <oldname> <newname>

#  unzip 
sudo apt-get install unzip
unzip <file>

# ---- File Search ------

# scroll result
ls | less

# count number of files in a directory
ls /user | wc -l

# find a file
find / -name 'program.c' 

# find files containing a text string. -r for subdirectories
grep -r "text string to search" <directorypath>

# ---- Disk/File Size check ----

# file size
# -l 	Displays file types, owner, group, size, date and filename.
# -a 	Displays all files, including hidden files (suffixed with “.”). 
# -h 	human readable format, e.g. 1k, 100MB, 1G. 	
ls -lah

# disk space usage
df -k
```

## network and system
```bash
# check internet connection
curl -Is http://www.shellhacks.com | head -1

# list of all sockets in use
netstat -a 

# power down now
shutdown -h now
reboot -h now

# execute 'ntpdate' with the ntp daemon already up and running, use the following command which uses a different port
ntpdate -u pool.ntp.org 

# install Linux / UNIX *.tar.gz tarball files
http://www.cyberciti.biz/faq/install-tarballs/

# find out what version of Linux
cat /etc/*-release
```

# CentOS

```
adduser username
passwd username  # Use the passwd command to update the new user's password.

usermod -aG wheel username # By default, on CentOS, members of the wheel group have sudo privileges.
```

# Others #

## Windows Remote desktop ##
install XRDP
```bash
sudo apt-get install xrdp
```

## Install JDK ##
 * Ubuntu 12 + Oracle JDK 6
 
```bash
# detail: http://jamesslootweg.com/view/Manually_Install_Oracle_JDK_6_on_Ubuntu_12.10
# download jdk-6u43-linux-i586.bin
chmod a+x jdk-6u43-linux-i586.bin
sudo jvm/jdk-6u43-linux-i586.bin
sudo mkdir /usr/lib/jvm 
sudo mv jdk1.6.0_43 /usr/lib/jvm/
sudo ln -s -b /usr/lib/jvm/jdk1.6.0_43/jre/bin/java /etc/alternatives/java
sudo ln -s -b /usr/lib/jvm/jdk1.6.0_43/jre/bin/java /usr/bin/java
```

 *  Oracle JDK 7
 
```bash
http://linuxdrops.com/install-jre-or-jdk-7-on-centos-rhel-fedora-ubuntu-debian/
```


## Install Leiningen ##

download the "lein.bat" in a path folder to start self-install

sudo apt-get install leiningen ??


```bash
# find a path to install leiningen,  or add a folder (/usr/bin/leiningen) to the paths
echo $PATH 
sudo mkdir /usr/bin/leiningen

//add the leiningen folder to your $PATH

nano ~/.bashrc 
// Open nano and add the following line to the bottom of .bashrc
export PATH=$PATH:/usr/bin/leiningen // Add this line. 

source .bashrc // Reload .bashrc
echo $PATH // check that your leiningen folder is added

cd /usr/bin/leiningen

// download "lein.bat" to the path
sudo wget --no-check-certificate https://raw.github.com/technomancy/leiningen/stable/bin/lein 

// start self-install
sudo chmod +x lein
./lein self-install
lein repl // To test, Crtl + Z to exit repl

```
