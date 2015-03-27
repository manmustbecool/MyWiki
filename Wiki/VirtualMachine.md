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
