package de.janburzinski.rank.cache;

import java.util.HashMap;

public class Cache<K, V> {
    public HashMap<K, V> cache;

    public Cache() {
        cache = new HashMap<>();
    }

    public V get(K key) {
        return cache.get(key);
    }

    public void set(K key, V value) {
        cache.put(key, value);
    }

    public boolean has(K key) {
        return cache.containsKey(key);
    }

    public void remove(K key) {
        cache.remove(key);
    }
}
