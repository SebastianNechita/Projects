package model.expression;

import exceptions.InterpreterException;
import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;

public class ReadExpression implements Expression {
    private String variable;

    public ReadExpression(String variable) {
        this.variable = variable;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {
        if(!dict.exists(variable))
            throw new InterpreterException("The variable " + variable + " is not found in the symbol table\n");
        int value = dict.getValue(variable);
        if(!heap.exists(value))
            throw new InterpreterException("The address " + value + " is not found in the heap\n");
        return heap.get(value);
    }

    @Override
    public String toString(){
        return "read(" + variable + ")";
    }
}
