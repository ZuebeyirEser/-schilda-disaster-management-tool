package de.thab.algo.abstractdatastructures.list;

import de.thab.algo.abstractdatastructures.list.List;

public class LinkedList<T> implements List<T> {
    private Node<T> head;
    private int size;
    public static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }


    @Override
    public void add(T element) {
        Node<T> newNode = new Node<>(element);
        if (head == null) {
            head = newNode;
        } else {
            Node<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    // this methods removes by index
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            head = head.next;
        } else {
            Node<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    public void deleteByKey(T key) {
        if (isEmpty()) {
            System.out.println("List is empty, nothing to delete.");
            return;
        }

        // If the head node itself holds the key to be deleted
        if (head.data.equals(key)) {
            head = head.next;
            size--;
            return;
        }

        // Search for the key to be deleted, keep track of the previous node
        Node<T> current = head;
        Node<T> prev = null;

        while (current != null && !current.data.equals(key)) {
            prev = current;
            current = current.next;
        }

        // If key was not present in the list
        if (current == null) {
            System.out.println("Key not found in the list.");
            return;
        }

        // Unlink the node from linked list
        prev.next = current.next;
        size--;
    }
    public boolean contains(T element) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
    public void print() {
        if (isEmpty()) {
            System.out.println("List is empty");
            return;
        }

        Node<T> current = head;
        while (current != null) {
            System.out.print(current.data);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }

}
