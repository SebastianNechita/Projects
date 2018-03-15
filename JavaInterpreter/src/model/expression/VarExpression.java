package model.expression;

import exceptions.InterpreterException;
import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;

public class VarExpression implements Expression {
    private String var;

    public VarExpression(String var) {
        this.var = var;
    }

    @java.lang.Override
    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {
        if(dict.exists(var))
            return dict.getValue(var);
        throw new InterpreterException("Error variable " + var + " was not defined\n");
    }

    @java.lang.Override
    public java.lang.String toString() {
        return var;
    }
}
