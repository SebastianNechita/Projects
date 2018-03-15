package model.dateStructures;

import java.util.Map;
import java.util.Set;

public interface IProcTable<K, V> {
    public boolean contains(K key);
    public V get(K key);
    public void add(K key, V val);
    public void modify(K key, V val);
    public Set<Map.Entry<K, V>> getAll();
    public V remove(K key);
}
