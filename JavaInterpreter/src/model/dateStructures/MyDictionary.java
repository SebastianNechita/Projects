package model.dateStructures;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyDictionary<K, V> implements IDictionary<K, V>{
    private Map<K, V> map;

    public MyDictionary() {
        map = new HashMap<>();
    }

    @Override
    public boolean exists(K key) {
        return map.containsKey(key);
    }

    @Override
    public V getValue(K key) {
        return map.get(key);
    }

    @Override
    public void setValue(K key, V value) {
        map.put(key, value);
    }

    @Override
    public void add(K key, V value) {
        map.put(key, value);
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        for(Map.Entry<K, V> it : map.entrySet()){
            str.append(it.getKey());
            str.append("-");
            str.append(it.getValue());
            str.append(";\n");
        }
        return str.toString();
    }

    @Override
    public Map<K, V> getContent() {
        return map;
    }

    @Override
    public void setContent(Map<K, V> map) {
        this.map = map;
    }

    @Override
    public Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }

    @Override
    public IDictionary<K, V> clone() {
        IDictionary<K, V> dict = new MyDictionary<>();
        for(Map.Entry<K, V> entry : map.entrySet()){
            dict.add(entry.getKey(), entry.getValue());
        }
        return dict;
    }
}
