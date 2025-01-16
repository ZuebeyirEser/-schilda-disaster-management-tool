package de.thab.algo.abstractdatastructures;

import java.util.Iterator;

public class CustomSet<T> implements Iterable<T> {
    private final int SIZE = 100;
    private Object[] elements;
    private int count;

    public CustomSet() {
        elements = new Object[SIZE];
        count = 0;
    }

    public boolean add(T value) {
        if (contains(value)) return false;
        if (count >= SIZE) throw new IllegalStateException("Set is full");
        elements[count++] = value;
        return true;
    }

    public boolean contains(T value) {
        for (int i = 0; i < count; i++) {
            if (elements[i].equals(value)) return true;
        }
        return false;
    }

        /**
     * Checks if this set contains all elements of another CustomSet.
     *
     * @param other the other CustomSet to compare against
     * @return true if this set contains all elements of the other set, false otherwise
     */
    public boolean containsAll(CustomSet<T> other) {
        for (T element : other) {
            if (!contains(element)) {
                return false;
            }
        }
        return true;
    }


    public boolean remove(T value) {
        for (int i = 0; i < count; i++) {
            if (elements[i].equals(value)) {
                elements[i] = elements[--count];
                elements[count] = null;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return count;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < count;
            }

            @Override
            public T next() {
                if (!hasNext()) throw new IllegalStateException("No more elements");
                return (T) elements[index++];
            }
        };
    }
}
