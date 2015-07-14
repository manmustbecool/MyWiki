---
title: "java Collection"
---

## Collection ##

![images\Java-Collection-Framework.PNG](images\Java-Collection-Framework.PNG)

### List ###

  * cares about index
  * index related methods like get(int index) are available only in List based classes
  * lists are ordered by index position
  * can add elements at a particular index or at the end of the list

**ArrayList**

  * growable array
  * gives fast iteration and fast random access
  * ordered but not sorted
  * implements RandomAccess interface(marker interface), hence ArrayList supports fast(generally constant time) random access
  * choose this over LinkedList when you need fast iteration but aren't as likely to be doing a lot of insertion and deletion
  * methods are not synchronized

**Vector**

  * holdover from earliest days of java; Vector and Hashtable were the original collections (rest were added at 1.2 and 1.4)
  * same as ArrayList but methods are synchronized for thread safety
  * due to synchronized, Vector is slow in performance
  * thread safety can be obtained from Collections utility methods as well instead of using Vector
  * Vector is the only class other than ArrayList to implement RandomAccess

**LinkedList**

  * is ordered by index position(like ArrayList), except that elements are doubly-linked to one another methods to add/remove from beginning or end
  * easy choice to implement stack or queue
  * iterates more slowly than ArrayList
  * but a good choice for fast insertion and deletion
  * as of java 5, it was enhanced to implement Queue interface as well; hence supports peek(), poll(), offer() and such methods

### Set ###

  * cares about uniqueness
  * doesn't allow duplicates
  * bases itself on equals() method implementation

**HashSet**

  * unsorted and unordered set
  * uses hashcode of the object being inserted; the more efficient hashCode() implementation, the better access performance
  * use this when you want no duplicates and don't care about order when you iterate through the elements

**LinkedHashSet**

  * an ordered version of HashSet
  * maintains doubly-linked list across all elements
  * if you care about iteration order, use this
  * it lets you iterate through in the order in which elements were inserted(or added)

 when using HashSet or LinkedHashSet, you must override hashCode(), otherwise uniqueness of objects is not guaranteed

**TreeSet**

  * is one of the two sorted collections (the other being TreeMap)
  * uses Red-Black tree structure(special binary search tree with minimum possible height)
  * guarantees that the elements will be in ascending order according to natural order
  * lets you specify your own ordering rules by using Comparable or Comparator
  * as of java 6, TreeSet also implements NavigableSet

### Map ###

  * cares about unique identifiers for each value
  * both key and value are objects
  * relies on equals() to determine two keys are same or not

**HashMap**

  * unsorted unordered map
  * access performance depends on the hashCode() implementation for your keys
  * allows one null key and multiple null values

**Hashtable**

  * legacy class
  * synchronized counter part to HashMap
  * doesn't allow null keys or values(throws NullPointerException in such cases)

**LinkedHashMap**

  * maintains insertion order(optionally access order, thru a flag to the constructor)
  * slower than HashMap for adding removing elements
  * faster iteration than HashMap

**TreeMap**

  * sorted Map and hence ordered in the natural order
  * lets you define custom sort order through Comparable or Comparator interfaces
  * as of java 6, implements NavigableMap

### Queue ###

  * list of TO-DOs
  * typically thought of as FIFO(first-in first-out)
  * supports standard Collection methods + methods to add/subtract/review queue elements

**PriorityQueue**

  * basic queues can be implemented with LinkedList
  * purpose of PriorityQueue is to create "priority-in priority-out" queue as opposed to "first-in first-out)
  * elements are ordered according to natural order or according to the Comparator set
  * elements that are sorted first are accessed first; ordering represents the relative priority between elements

## Basic ##

### Compare and Sort ###

Comparable

  * java.lang.Comparable
  * int obj1.compareTo(obj2)
  * returns negative int when obj2 is greater, positive int when obj1 is greater, zero when both are equal
  * you must modify the class whose instances you want to sort
  * only one sort sequence can be done
  * implemented frequently by APIs such as String, Wrapper classes, Date, Calendar, etc

Comparator

  * java.util.Comparator
  * int compare(obj1, obj2)
  * return value is same as Comparable
  * you build a class separate from the class whose instances you want to sort
  * many sort sequences can be created
  * meant to be implemented by third party classes
  * both Comparable and Comparator are interfaces; they are appropriately put in different packages based on their purpose

```java
class Order implements Comparable<Order> {
    private int orderId;
    private int amount;
    private String customer;

    // Sorting on orderId is natural sorting for Order.
    @Override
    public int compareTo(Order o) {
        return this.orderId > o.orderId ? 1 : (this.orderId < o.orderId ? -1 : 0);
    }
 
    //Comparator implementation to Sort Order object based on Amount
    public static class OrderByAmount implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.amount > o2.amount ? 1 : (o1.amount < o2.amount ? -1 : 0);
        }
    }

    // Anohter Comparator implementation based upon customer name.
    public static class OrderByCustomer implements Comparator<Order> {
        @Override
        public int compare(Order o1, Order o2) {
            return o1.customer.compareTo(o2.customer);
        }
    }
}
```

Collection sort:

```java
// use comparable
Collections.sort(orders);
Collections.sort(orders, Collections.reverseOrder());

// use comparator
Collections.sort(orders, new Order.OrderByAmount());
Collections.sort(termsList, Collections.reverseOrder(new Order.OrderByCustomer()));
```

  * `Arrays.sort()` provides sorting capability for arrays in the same way Collections.sort() provides
  * both Arrays.sort() and Collections.sort() are static methods; both alter the same object they are sorting and do not return result as a separate object;
  * loads of sort() overloaded methods to sort primitive data arrays in their natural order without the use of Comparator
  * if two objects that are not comparable present in the array or collection, the sort will fail

String natural order:
  * space comes before letters
  * uppercase letters come before lowercase letters

### Collection and Array conversion ###

```java
// Collection to Arrays
String [] countries = list.toArray(new String[list.size()]);

// Arrays to Collection
List list1 = Arrays.asList(countries);
```

  * Arrays.asList() copies an array into a List; this list is backed by the original array; changes to the returned list write through to the array; that is changing either the array or the list, updates the other automatically; array and list both become joined at the hip;(makes the given array as the internal array for the new list)

### Searching Array and Collection ###

  * searches are performed using the binarySearch() method
  * successful search returns the int index of the element searched
  * unsuccessful search returns insertion point(index) as return value; insertion point is where the searched element can be added without affecting the sorted order of other elements
  * 0 and positive return values indicate successful search
  * negative return value indicates insertion point on unsuccessful search
  * since 0 is not available for negative range, -1 indicates 0th index, -2 indicates 1st index and so on; so insertion point is calculated by [-n -1] where n is the signed insertion index returned.

  * the collection/array being searched must be sorted before you can search it
  * attempting to search an unsorted array or collection will result in unpredictable results(not compiler error :))
  * the collection/array sorted in natural order can only be searched in natural order using binarySearch() method; hence no Comparator is allowed
  * comparators cannot be used while searching arrays of primitives(nobody wants your own way of arranging integers, strings, etc)
  * the same comparator used to sort has to be used while searching; otherwise the consequences are dire
  * even trying to search an array or collection that has been reverse-sorted(descending) will return incorrect result

SEARCH RULE --> SORT : SAME\_COMPARATOR : ASCENDING


```java
int index = Collections.binarySearch(list, "John");

int index = Collections.binarySearch(list, key, comparator) 
```

### Sub Collection ###

`.subList()/.subSet()/.sub...` methods only returns a view of the portion of the original collection. The sub list will be updated if the original list be updated, and vice versa.

### Synchronized collection ###

Collection synchronized wrapper method
return a a wrapped instance which has all get, put methods synchronized
```java
Collections.synchronizedMap(hashMap)

Collections.synchronizedList(list)
```

ConcurrentHashMap:
  * ConcurrentHashMap stores key-value pair is stored in different segments. Different segments can be locked by different threads in high concurrent executions.
  * In Collections.synchronizedMap, the whole hash map only can be locked by one thread each time.


---

ref: http://murkymind.blogspot.ie/2012/05/gist-notes-7-java-generics-and.html