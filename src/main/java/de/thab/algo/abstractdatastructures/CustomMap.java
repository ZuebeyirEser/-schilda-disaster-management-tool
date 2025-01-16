package de.thab.algo.abstractdatastructures;

public interface CustomMap<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    boolean remove(K key);

    int size();

    void clear();

    /**
     * Returns a set of all key-value pairs in the map.
     *
     * @return a set of all entries in the map
     */
    CustomSet<Entry<K, V>> entrySet();

    /**
     * Represents a key-value pair in the map.
     */
    interface Entry<K, V> {
        K getKey();

        V getValue();

        void setValue(V value);
    }
}
