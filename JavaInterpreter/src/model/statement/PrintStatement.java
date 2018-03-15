package model.statement;

import model.PrgState;
import model.expression.Expression;

public class PrintStatement implements Statement{
    private Expression e;

    public PrintStatement(Expression e) {
        this.e = e;
    }

    @Override
    public PrgState execute(PrgState state) {
        int val = e.eval(state.getSymbolTable(), state.getHeap());
        state.getOutput().add(val);
        return null;
    }

    @Override
    public String toString() {
        return "print(" + e + ")";
    }
}
