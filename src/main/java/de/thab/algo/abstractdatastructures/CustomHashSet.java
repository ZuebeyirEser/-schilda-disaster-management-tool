package de.thab.algo.abstractdatastructures;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomHashSet<T> extends CustomSet<T> {
    private static final Object DUMMY_VALUE = new Object();
    private final CustomHashMap<T, Object> map;

    public CustomHashSet() {
        this.map = new CustomHashMap<>();
    }

    /**
     * Constructor to initialize the set with elements from a collection.
     *
     * @param collection the collection whose elements are to be placed into this set
     */
    public CustomHashSet(Iterable<T> collection) {
        this();
        for (T element : collection) {
            add(element);
        }
    }

    @Override
    public boolean add(T value) {
        if (map.containsKey(value)) {
            return false;
        }
        map.put(value, DUMMY_VALUE);
        return true;
    }

    @Override
    public boolean contains(T value) {
        return map.containsKey(value);
    }

    @Override
    public boolean remove(T value) {
        return map.remove(value);
    }

    @Override
    public int size() {
        return map.size();
    }

    public void clear() {
        map.clear();
    }

    /**
     * Converts the set to a List.
     *
     * @return a List containing all elements of the set.
     */
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            list.add(map.getKeyAtIndex(i));
        }
        return list;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < map.size();
            }

            @Override
            public T next() {
                return map.getKeyAtIndex(index++);
            }
        };
    }
}
