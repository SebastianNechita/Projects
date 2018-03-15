package model.expression;


import exceptions.*;
import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;

public class ArithmeticExpression implements Expression {
    private Expression l, r;
    private char c;

    public ArithmeticExpression(Expression l, Expression r, char c) {
        this.l = l;
        this.r = r;
        this.c = c;
    }

    @java.lang.Override
    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {
        int vl = l.eval(dict, heap);
        int vr = r.eval(dict, heap);
        switch (c){
            case '+':
                return vl + vr;
            case '-':
                return vl - vr;
            case '*':
                return vl * vr;
            case '/':
                if(vr == 0){
                    throw new ZeroDivisionException("You have divided by 0\n");
                }
                return vl / vr;
        }
        throw new InvalidSymbolException("The sign " + c + " does not exist\n");
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "" + l + c + r;
    }
}
