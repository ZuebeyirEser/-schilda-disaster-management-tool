package de.thab.algo.abstractdatastructures;

public class CustomQueue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    private static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    public CustomQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Adds an element to the queue.
     *
     * @param value the element to be added
     * @return true if the element is successfully added
     */
    public boolean add(T value) {
        Node<T> newNode = new Node<>(value);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    /**
     * Retrieves and removes the head of the queue, or returns null if the queue is empty.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public T poll() {
        if (head == null) {
            return null; // Queue is empty
        }
        T value = head.value;
        head = head.next;
        if (head == null) {
            tail = null; // Queue becomes empty
        }
        size--;
        return value;
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the queue.
     *
     * @return the number of elements in the queue
     */
    public int size() {
        return size;
    }
}
