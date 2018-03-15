package model.statement;

import model.dateStructures.IDictionary;
import model.PrgState;
import model.expression.Expression;

public class AsignmentStatement implements Statement {
    private String variable;
    private Expression expr;

    public AsignmentStatement(String variable, Expression expr) {
        this.variable = variable;
        this.expr = expr;
    }

    @java.lang.Override
    public PrgState execute(PrgState state) {
        IDictionary<String, Integer> dict = state.getSymbolTable();
        int rez = expr.eval(dict, state.getHeap());
        if(dict.exists(variable))
            dict.setValue(variable, rez);
        else
            dict.add(variable, rez);
        return null;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return variable + "=" + expr;
    }
}
