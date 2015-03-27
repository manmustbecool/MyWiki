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


# Networking in VM #

Networking mode Overview:

![images\vm_networking.png](images\vm_networking.png)

* Bridged mode
    + The guest machine becomes a peer of all the other computers on that network virtualy. All network traffics will through the host network interface card (NIC). See the above picture, dot lines/links virtually connect guest machines's NICs to the network, but solid lines conect all NICs representing real traffics.
   
* Internal mode
    + Similar to the bridged mode, but network traffics between guest machines can be hiden from the host machine NIC for a security reason (See above picture, the direct link between two guest machines).

* NAT (Network Address Translation) mode
    + The guest machine is sharing the Mac's IP address of your Host machine.
    
* Host-only mode
    + The guest machine's network world is wholly within your Host machine.



### Bridged mode ###

* Disadvantages:
    1. Having troubles with accessing the network at all if your VM is not added to the domain.
    2. A generated mac address of the virtual machine is recognized by the company firewall


### NAT mode ###

#### VMware: Port forward with NAT setup ####
http://geekswithblogs.net/NewThingsILearned/archive/2009/11/13/connect-to-vmware-virtual-machines-using-remote-desktop.aspx

As the Hadoop uses SSH for node communications.

  * Under the "Edit" Menu > "Virtual Network Editor"
  * Click the "Add" for Port Forwarding
  * Then add a port forwarding configuration. Forward incoming port 10000 to 22 for SSH

Now, we can SSH the guest machine with the host IP address with port 10000.
e.g. ssh -p 10000 guestVMmachine


#### VirtualBox: Port forward with NAT setup ####

Remote desktop connection on a Windows guest machine

The remote desktop must be enabled first in the guest machine (default is disabled in windows server 2008 [r2](https://code.google.com/p/bpaas/source/detail?r=2)).

  * Add a port forwarding rule in VBox network setting. The 3389 is default port of remote desktop
```bash
Rule1 TCP hostIp 3389 guestIp 3389
```

  * The Windows server uses dynamic port for some services:
```bash
# show the dynamic ports
netsh int ipv4 show dynamicport tcp
netsh int ipv6 show dynamicport udp
# set the dynamic ports
netsh int ipv4 set dynamicport tcp start=10000 num=1000
netsh int ipv4 set dynamicport udp start=10000 num=1000
```

    In this case, multiple port forwarding can be simply done in VBox command line. (http://tombuntu.com/index.php/2008/12/17/configure-port-forwarding-to-a-virtualbox-guest-os/)
```bash
$ VBoxManage setextradata "GuestVMName" "VBoxInternal/Devices/e1000/0/LUN#0/AttachedDriver/Config/DescriptiveName/Protocol" TCP
$ VBoxManage setextradata "GuestVMName" "VBoxInternal/Devices/e1000/0/LUN#0/AttachedDriver/Config/DescriptiveName/GuestPort" 100
$ VBoxManage setextradata "GuestVMName" "VBoxInternal/Devices/e1000/0/LUN#0/AttachedDriver/Config/DescriptiveName/HostPort" 100
$ VBoxManage setextradata "GuestVMName" "VBoxInternal/Devices/e1000/0/LUN#0/AttachedDriver/Config/DescriptiveName/PortCount" 40
```

    The adapters are referred to by the following keys:
    * First PCNet: pcnet/0
    * Second PCNet: pcnet/1
    * First Intel PRO/1000: e1000/0
    * Second Intel PRO/1000: e1000/1
    * Third Intel PRO/1000: e1000/2

    Updating will be refected on the VM XML. ` <ExtraDataItem name="VBoxInternal/D ... ` will be added.

### Host-only mode 

#### VirtualBox: Add hotst-only network ####

Go to File > Preferences > Network > Add host-only network. The DHCP can also be configured in here.

Reference site: 
<a href='http://communities.vmware.com/docs/DOC-2527'>http://communities.vmware.com/docs/DOC-2527</a> 

<a href='http://www.virtualbox.org/manual/ch06.html'>http://www.virtualbox.org/manual/ch06.html</a>

# Others
start vm in backgroup without GUI.

```bash
VBoxManage startvm $VM --type headless
# Windows
"C:/Program Files/Oracle VM VirtualBox/VBoxManage.exe" startvm ubuntu --type headless
```

To shut it down, request the shut down from the guest.
