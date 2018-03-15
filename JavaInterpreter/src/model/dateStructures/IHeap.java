package model.dateStructures;

import java.util.Map;
import java.util.Set;

public interface IHeap<K, V> {
    public V get(K key);
    public void add(K key, V value);
    public void put(K key, V value);
    public boolean exists(K key);
    public Set<Map.Entry<K, V>> getAll();
    public void setContent(Map<K, V> map);
}
