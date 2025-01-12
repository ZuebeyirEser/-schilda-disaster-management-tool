package de.thab.algo.abstractdatastructures.queue;

import de.thab.algo.datastructure.MinHeap;
import java.util.NoSuchElementException;

/**
 * A custom priority queue implementation using a MinHeap.
 */
public class CustomPriorityQueue {
    private MinHeap minHeap;

    /**
     * Constructs a CustomPriorityQueue with the specified capacity.
     *
     * @param capacity The maximum number of elements the queue can hold.
     */
    public CustomPriorityQueue(int capacity) {
        minHeap = new MinHeap(capacity);
    }

    /**
     * Adds an element to the priority queue.
     *
     * @param vertex The vertex to be added.
     * @param key The priority key associated with the vertex.
     * @throws IllegalStateException if the priority queue is full.
     */
    public void enqueue(int vertex, int key) {
        if (!minHeap.insertKey(vertex, key)) {
            throw new IllegalStateException("Priority Queue is full");
        }
    }

    /**
     * Removes and returns the element with the highest priority (lowest key).
     *
     * @return The HeapNode with the highest priority.
     * @throws NoSuchElementException if the priority queue is empty.
     */
    public MinHeap.HeapNode dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        return minHeap.extractMin();
    }

    /**
     * Returns, but does not remove, the element with the highest priority.
     *
     * @return The HeapNode with the highest priority.
     * @throws NoSuchElementException if the priority queue is empty.
     */
    public MinHeap.HeapNode peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue is empty");
        }
        return minHeap.getMin();
    }

    /**
     * Checks if the priority queue is empty.
     *
     * @return true if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return minHeap.current_heap_size == 0;
    }

    /**
     * Decreases the key of a given vertex in the priority queue.
     *
     * @param vertex The vertex whose key is to be decreased.
     * @param newKey The new key value.
     */
    public void decreaseKey(int vertex, int newKey) {
        minHeap.decreaseKey(vertex, newKey);
    }
}
