package model.dateStructures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<K, V> implements IHeap<K, V> {
    private Map<K, V> map = new HashMap<>();

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void add(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public boolean exists(K key) {
        return map.containsKey(key);
    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }

    @Override
    public void setContent(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        for(Map.Entry<K, V> ent : map.entrySet()){
            str.append(ent.getKey() + "->" + ent.getValue() + "\n");
        }
        return str.toString();
    }
}
