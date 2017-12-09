/******************************************************************************
 *  Compilation:  javac-algs4 Deque.java
 *  Execution:    java-algs4 Deque
 *
 *  A Deque.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;
    private Node<Item> last;
    private int size;
    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }
        Node<Item> node = new Node<>();
        node.item = item;

        if (isEmpty()) {
            first = node;
            last = node;
        } else {
            node.next = first;
            first.prev = node;
            first = node;
        }

        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item can not be null");
        }

        Node<Item> node = new Node<>();
        node.item = item;

        if (isEmpty()) {
            first = node;
            last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;
        }

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> oldFirst = first;
        first = first.next;

        if (first == null) {
            last = null;
        } else {
            first.prev = null;
            oldFirst.next = null;
        }

        size--;
        return oldFirst.item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Item> oldLast = last;
        last = last.prev;

        if (last == null) {
            first = null;
        } else {
            last.next = null;
            oldLast.prev = null;
        }

        size--;
        return oldLast.item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addFirst(3);
        if (deque.size() != 3) {
            StdOut.println("addFirst size failed");
            return;
        }
        if (deque.removeFirst() != 3) {
            StdOut.println("removeFirst 3 failed");
            return;
        }
        if (deque.removeFirst() != 2) {
            StdOut.println("removeFirst 2 failed");
            return;
        }
        if (deque.removeFirst() != 1) {
            StdOut.println("removeFirst 1 failed");
            return;
        }
        deque.addLast(4);
        if (deque.removeLast() != 4) {
            StdOut.println("removeLast 4 failed");
            return;
        }
        deque.addFirst(5);
        if (deque.removeLast() != 5) {
            StdOut.println("addFirst and removeLast 5 failed");
            return;
        }
        deque.addLast(6);
        if (deque.removeFirst() != 6) {
            StdOut.println("addLast and removeFirst 6 failed");
            return;
        }
        StdOut.println("deque passed");
    }

    private class Node<Item> {
        private Item item;
        private Node<Item> prev;
        private Node<Item> next;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> curNode = first;

        public boolean hasNext() {
            return curNode != null && curNode.next != null;
        }

        public Item next() {
            if (isEmpty() || !hasNext()) {
                throw new NoSuchElementException();
            }
            curNode = curNode.next;
            return curNode.item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}