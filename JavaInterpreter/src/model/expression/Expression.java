package model.expression;

import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;

public interface Expression {
    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap);
}
