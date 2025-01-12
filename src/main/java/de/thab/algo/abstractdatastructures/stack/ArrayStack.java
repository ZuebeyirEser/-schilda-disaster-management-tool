package de.thab.algo.abstractdatastructures.stack;

public class ArrayStack<T> {
    private int top;
    private int size;
    private T[] stack;

    public ArrayStack(int size) {
        this.size = size;
        this.top = - 1;
        this.stack = (T[]) (new Object[size]);
    }

    public void push(T element) {
        // check if stack is full
        if (top == size - 1) {
            throw new StackOverflowError("Stack is full!");
        }
        top++;
        stack[top] = element;
    }
    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        top--;
        return stack[top + 1];
    }
    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
        return stack[top];
    }
    public boolean isEmpty() {
        return top == -1;
    }

}
