package model.expression;

import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;

public class ConstExpression implements Expression {
    private int cst;

    public ConstExpression(int cst) {
        this.cst = cst;
    }

    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {
        return cst;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "" + cst;
    }
}
