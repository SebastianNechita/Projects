package model.statement;

import exceptions.InterpreterException;
import model.PrgState;
import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;
import model.expression.Expression;

public class WriteStatement implements Statement{
    private String variable;
    private Expression expr;

    public WriteStatement(String variable, Expression expr) {
        this.variable = variable;
        this.expr = expr;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Integer> dict = state.getSymbolTable();
        IHeap<Integer, Integer> heap = state.getHeap();
        if(!dict.exists(variable))
            throw new InterpreterException(variable + " is not present in the symbol table\n");
        int addr = dict.getValue(variable);
        if(!heap.exists(addr))
            throw new InterpreterException(addr + " is not found in the heap\n");
        heap.put(addr, expr.eval(dict, heap));
        return null;
    }

    @Override
    public String toString() {
        return "Write(" + variable + ", " + expr + ")";
    }
}
