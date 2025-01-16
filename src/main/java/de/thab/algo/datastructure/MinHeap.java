package de.thab.algo.datastructure;

import java.util.Arrays;

/**
 * A min-heap implementation for use in graph algorithms.
 */
public class MinHeap {
    /**
     * Represents a node in the heap, containing a vertex and its associated key.
     */
    public static class HeapNode {
        private int vertex;
        private int key;

        /**
         * Constructs a HeapNode with the given vertex and key.
         *
         * @param vertex The vertex number.
         * @param key The key (priority) associated with the vertex.
         */
        HeapNode(int vertex, int key) {
            this.vertex = vertex;
            this.key = key;
        }
        /**
         * @return The vertex number.
         */
        public int getVertex() {
            return vertex;
        }

        /**
         * @return The key (priority) of the vertex.
         */
        public int getKey() {
            return key;
        }
    }

    public HeapNode[] heapArray;
    public int capacity;
    public int current_heap_size;
    // Store vertex to index mapping for O(1) lookups
    private int[] vertexToIndex;

    /**
     * Constructs a MinHeap with the given capacity.
     *
     * @param n The capacity of the heap.
     */
    public MinHeap(int n) {
        capacity = n;
        heapArray = new HeapNode[capacity];
        vertexToIndex = new int[n];
        current_heap_size = 0;

        Arrays.fill(vertexToIndex, -1);
    }

    /**
     * Swaps two nodes in the heap and updates their indices.
     *
     * @param arr The heap array.
     * @param a Index of the first node.
     * @param b Index of the second node.
     */
    private void swap(HeapNode[] arr, int a, int b) {
        // Update vertex to index mapping
        vertexToIndex[arr[a].vertex] = b;
        vertexToIndex[arr[b].vertex] = a;

        // Swap heap nodes
        HeapNode temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }
    /**
     * @param key The index of a node.
     * @return The index of the parent node.
     */
    private int parent(int key) {
        return (key - 1) / 2;
    }

    /**
     * @param key The index of a node.
     * @return The index of the left child.
     */
    private int left(int key) {
        return 2 * key + 1;
    }

    /**
     * @param key The index of a node.
     * @return The index of the right child.
     */
    private int right(int key) {
        return 2 * key + 2;
    }

    /**
     * Inserts a new key for the given vertex.
     *
     * @param vertex The vertex number.
     * @param key The key to be inserted.
     * @return true if insertion was successful, false if heap is full.
     */
    public boolean insertKey(int vertex, int key) {
        if (current_heap_size == capacity) {
            return false;
        }

        int i = current_heap_size;
        heapArray[i] = new HeapNode(vertex, key);
        vertexToIndex[vertex] = i;
        current_heap_size++;

        while (i != 0 && heapArray[i].key < heapArray[parent(i)].key) {
            swap(heapArray, i, parent(i));
            i = parent(i);
        }
        return true;
    }

    /**
     * Decreases the key of a given vertex.
     *
     * @param vertex The vertex whose key is to be decreased.
     * @param new_val The new key value.
     * @throws IllegalArgumentException if the new key is greater than the current key or if the vertex is invalid.
     */
    public void decreaseKey(int vertex, int new_val) {
        if (vertexToIndex[vertex] == -1) {
            return; // Vertex not in the heap, ignore decrease
        }
        int i = vertexToIndex[vertex];

        if (i < 0 || i >= current_heap_size) {
            throw new IllegalArgumentException("Invalid vertex");
        }

        if (new_val > heapArray[i].key) {
            throw new IllegalArgumentException("New key is greater than current key");
        }

        heapArray[i].key = new_val;

        while (i != 0 && heapArray[i].key < heapArray[parent(i)].key) {
            swap(heapArray, i, parent(i));
            i = parent(i);
        }
    }

    /**
     * @return The minimum element of the heap.
     * @throws IllegalStateException if the heap is empty.
     */
    public HeapNode getMin() {
        if (current_heap_size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heapArray[0];
    }
    /**
     * Extracts and returns the minimum element from the heap.
     *
     * @return The minimum element (root) of the heap.
     * @throws IllegalStateException if the heap is empty.
     */
    public HeapNode extractMin() {
        if (current_heap_size <= 0) {
            throw new IllegalStateException("Heap is empty");
        }

        HeapNode root = heapArray[0];
        vertexToIndex[root.vertex] = -1; // Mark as removed

        if (current_heap_size > 1) {
            heapArray[0] = heapArray[current_heap_size - 1];
            vertexToIndex[heapArray[0].vertex] = 0;
        }

        current_heap_size--;
        if (current_heap_size > 0) {
            MinHeapify(0);
        }

        return root;
    }
    /**
     * Maintains the min heap property(top to bottom and left to right) starting from the given index.
     *
     * @param i The index from which to start heapifying.
     */
    private void MinHeapify(int i) {
        int l = left(i);
        int r = right(i);

        int smallest = i;
        if (l < current_heap_size && heapArray[l].key < heapArray[smallest].key) {
            smallest = l;
        }
        if (r < current_heap_size && heapArray[r].key < heapArray[smallest].key) {
            smallest = r;
        }

        if (smallest != i) {
            swap(heapArray, i, smallest);
            MinHeapify(smallest);
        }
    }
}
