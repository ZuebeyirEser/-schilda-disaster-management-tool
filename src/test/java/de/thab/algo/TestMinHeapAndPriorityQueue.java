package de.thab.algo;

import de.thab.algo.abstractdatastructures.queue.CustomPriorityQueue;
import de.thab.algo.datastructure.MinHeap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestMinHeapAndPriorityQueue {
    private MinHeap minHeap;
    private CustomPriorityQueue priorityQueue;

    @BeforeEach
    void setUp() {
        minHeap = new MinHeap(10);
        priorityQueue = new CustomPriorityQueue(10);
    }

    @Test
    void testMinHeapInsertAndGetMin() {
        minHeap.insertKey(0, 15);
        minHeap.insertKey(1, 10);
        minHeap.insertKey(2, 20);
        minHeap.insertKey(3, 5);
        minHeap.insertKey(4, 30);

        assertEquals(5, minHeap.getMin().getKey(), "Minimum key should be 5");
    }

    @Test
    void testMinHeapDecreaseKey() {
        minHeap.insertKey(0, 15);
        minHeap.insertKey(1, 10);
        minHeap.insertKey(2, 20);
        minHeap.insertKey(3, 5);
        minHeap.insertKey(4, 30);

        minHeap.decreaseKey(2, 1);
        assertEquals(1, minHeap.getMin().getKey(), "Minimum key should be 1 after decreaseKey");
    }

    @Test
    void testMinHeapExtractMin() {
        minHeap.insertKey(0, 15);
        minHeap.insertKey(1, 10);
        minHeap.insertKey(2, 20);
        minHeap.insertKey(3, 5);
        minHeap.insertKey(4, 30);

        assertEquals(5, minHeap.extractMin().getKey(), "Extracted min key should be 5");
        assertEquals(10, minHeap.getMin().getKey(), "Next minimum key should be 10");
    }

    @Test
    void testCustomPriorityQueueEnqueueAndPeek() {
        priorityQueue.enqueue(0, 15);
        priorityQueue.enqueue(1, 10);
        priorityQueue.enqueue(2, 20);
        priorityQueue.enqueue(3, 5);
        priorityQueue.enqueue(4, 30);

        assertEquals(5, priorityQueue.peek().getKey(), "Peeked key should be 5");
    }

    @Test
    void testCustomPriorityQueueDecreaseKey() {
        priorityQueue.enqueue(0, 15);
        priorityQueue.enqueue(1, 10);
        priorityQueue.enqueue(2, 20);
        priorityQueue.enqueue(3, 5);
        priorityQueue.enqueue(4, 30);

        priorityQueue.decreaseKey(2, 1);
        assertEquals(1, priorityQueue.peek().getKey(), "Peeked key should be 1 after decreaseKey");
    }

    @Test
    void testCustomPriorityQueueDequeue() {
        priorityQueue.enqueue(0, 15);
        priorityQueue.enqueue(1, 10);
        priorityQueue.enqueue(2, 20);
        priorityQueue.enqueue(3, 5);
        priorityQueue.enqueue(4, 30);

        assertEquals(5, priorityQueue.dequeue().getKey(), "Dequeued key should be 5");
        assertEquals(10, priorityQueue.peek().getKey(), "Next peeked key should be 10");
    }

    @Test
    void testPriorityQueueIsEmpty() {
        assertTrue(priorityQueue.isEmpty(), "Priority queue should be empty initially");

        priorityQueue.enqueue(0, 15);
        assertFalse(priorityQueue.isEmpty(), "Priority queue should not be empty after enqueue");

        priorityQueue.dequeue();
        assertTrue(priorityQueue.isEmpty(), "Priority queue should be empty after dequeue");
    }
}
