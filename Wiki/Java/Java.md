---
title: "Java"
output: html_document
---


# Singleton using enum 

```java
public enum UserActivity {
    INSTANCE;

    private String name;

    private UserActivity() {
        this.name = "xxyy";
    }

    public void dostuff() {
     ...
    }
}

// use it as ...
UserActivity.INSTANCE.doStuff();
```

# Concurrency

Book : Java Concurrency in Practice 
http://jcip.net/

## Thread 

code : https://github.com/manmustbecool/MyWiki/tree/gh-pages/myWikiCode/src

 * extends **Thread**

 * implements **Runnable**

 * implements **Callable**

    * Similar to Runnable, but a Runnable does not return a result and cannot throw a checked exception.

## Variables

### Volatile

The value of this variable will never be cached thread-locally: all reads and writes will go straight to "main memory";

### Atomic Variables

All classes have get and set methods that work like reads and writes on volatile variables.
```java
private AtomicInteger c = new AtomicInteger(0);
```

### ConcurrentHashMap

Hashtable is similar to Collections.synchronizedMap(Map) -  only one thread can access the map at the same time. 

ConcurrentHashMap  concurrent modification of the Map from several threads without the need to block them.

## Synchronized

only a single thread can execute a block of code at the same time

```java
synchronized void dostuff(){
  ...
}
```

or

```java
void dostuff(){
  ...
  synchronized(this){...}
}

```

# Search #

http://bigocheatsheet.com/

## Binary Search ##

Complexities: average O(log(n))   worst O(log(n))   best O(1)

input sorted array

  1. Compare x with the middle element.
  1. If x matches with middle element, we return the mid index.
  1. Else If x is greater than the mid element, then x can only lie in right half subarray after the mid element. So we recur for right half.
  1. Else (x is smaller) recur for the left half.


code : https://github.com/manmustbecool/MyWiki/tree/gh-pages/myWikiCode/src


# Sort #

http://bigocheatsheet.com/

## Quick Sort ##

  1. Select a pivot, normally the middle one
  1. From both ends, swap elements and make all elements on the left less than the pivot and all elements on the right greater than the pivot
  1. Recursively sort left part and right part
  
  code : https://github.com/manmustbecool/MyWiki/tree/gh-pages/myWikiCode/src

## Bubble Sort ##

  1. Compare each pair of adjacent elements from the beginning of an array and, if they are in reversed order, swap them.
  1. If at least one swap has been done, repeat step 1.
  
  code : https://github.com/manmustbecool/MyWiki/tree/gh-pages/myWikiCode/src

# java 8 

http://winterbe.com/posts/2014/03/16/java-8-tutorial/



