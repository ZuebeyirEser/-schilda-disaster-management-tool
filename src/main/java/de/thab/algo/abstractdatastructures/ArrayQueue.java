package de.thab.algo.abstractdatastructures;

public class ArrayQueue<T> {
    private int head;
    private int tail;
    private int size;
    private T[] queue;

    //constructor
    public ArrayQueue(int size) {
        this.head = 0;
        this.tail = -1;
        this.size = size;
        this.queue = (T[]) (new Object[size]);
    }

    public void enqueue(T element){
        if (isFull()) {
            throw new ArrayIndexOutOfBoundsException("Queue is already full!");
        }
        tail++;
        tail = tail % size;
        queue[tail] = element;
    }
    public T dequeue(){
        if (isEmpty()) {
            throw new ArrayIndexOutOfBoundsException("Queue is already emptx!");
        }
        int temp = head++;
        head = head++;
        head = head % size;

        return queue[temp];
    }
    public boolean isEmpty() {
        return head > tail;
    }
    public boolean isFull() {
        return (tail + 1) % size == head - 1;
    }
    public void printQueue() {
        int i = head;
        do {
            System.out.println(queue[i % size]);
            i++;
        } while (i % size <= tail);
    }


}
