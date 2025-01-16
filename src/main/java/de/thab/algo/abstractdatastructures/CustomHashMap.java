package de.thab.algo.abstractdatastructures;

import java.util.function.Function;

public class CustomHashMap<K, V> implements CustomMap<K, V> {
    private static final int SIZE = 100;
    private CustomLinkedList<CustomMap.Entry<K, V>>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public CustomHashMap() {
        buckets = new CustomLinkedList[SIZE];
        for (int i = 0; i < SIZE; i++) {
            buckets[i] = new CustomLinkedList<>();
        }
        size = 0;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % SIZE;
    }

    @Override
    public void put(K key, V value) {
        int index = hash(key);
        CustomLinkedList<CustomMap.Entry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            CustomMap.Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                entry.setValue(value); // Update existing key
                return;
            }
        }
        bucket.add(new Entry<>(key, value));
        size++;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        CustomLinkedList<CustomMap.Entry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            CustomMap.Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        CustomLinkedList<CustomMap.Entry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            CustomMap.Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean remove(K key) {
        int index = hash(key);
        CustomLinkedList<CustomMap.Entry<K, V>> bucket = buckets[index];
        for (int i = 0; i < bucket.size(); i++) {
            CustomMap.Entry<K, V> entry = bucket.get(i);
            if (entry.getKey().equals(key)) {
                bucket.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            buckets[i] = new CustomLinkedList<>();
        }
        size = 0;
    }

    @Override
    public CustomSet<CustomMap.Entry<K, V>> entrySet() {
        CustomSet<CustomMap.Entry<K, V>> entries = new CustomSet<>();
        for (CustomLinkedList<CustomMap.Entry<K, V>> bucket : buckets) {
            for (int i = 0; i < bucket.size(); i++) {
                entries.add(bucket.get(i));
            }
        }
        return entries;
    }

        /**
     * Computes a value for the key if it is not already present.
     *
     * @param key the key for which to compute the value
     * @param mappingFunction the function to compute the value
     * @return the existing or newly computed value
     */
    public V computeIfAbsent(K key, Function<K, V> mappingFunction) {
        V value = get(key);
        if (value == null) {
            value = mappingFunction.apply(key);
            put(key, value);
        }
        return value;
    }

    private static class Entry<K, V> implements CustomMap.Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
    
        /**
     * Retrieves the key at a specific index in the map.
     *
     * @param index the index of the key to retrieve
     * @return the key at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public K getKeyAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        int currentIndex = 0;
        for (CustomLinkedList<CustomMap.Entry<K, V>> bucket : buckets) {
            for (int i = 0; i < bucket.size(); i++) {
                if (currentIndex == index) {
                    return bucket.get(i).getKey();
                }
                currentIndex++;
            }
        }

        throw new IllegalStateException("Index " + index + " not found");
    }
}
