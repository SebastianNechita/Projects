package model.dateStructures;

import java.util.Map;
import java.util.Set;

public interface IDictionary<T, R> {
    public boolean exists(T key);
    public R getValue(T key);
    public void setValue(T key, R value);
    public void add(T key, R value);
    public Set<Map.Entry<T, R> > getAll();
    public Map<T, R> getContent();
    public void setContent(Map<T, R> map);
    public IDictionary<T, R> clone();
}
