package model.dateStructures;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProcTable<K, V> implements IProcTable<K, V> {
    private Map<K, V> map = new ConcurrentHashMap<>();
    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void add(K key, V val) {
        map.put(key, val);
    }

    @Override
    public void modify(K key, V val) {
        map.put(key, val);
    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        for(Map.Entry<K, V> i : map.entrySet()){
            str.append(i.getKey());
            str.append("->");
            str.append(i.getValue());
            str.append(",");
        }
        return str.toString();
    }
}
