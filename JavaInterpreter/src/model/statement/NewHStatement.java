package model.statement;

import model.IDGenerator;
import model.PrgState;
import model.dateStructures.IDictionary;
import model.expression.Expression;

public class NewHStatement implements Statement {
    private String var_name;
    private Expression expr;

    public NewHStatement(String var_name, Expression expr) {
        this.var_name = var_name;
        this.expr = expr;
    }

    public String getVar_name() {
        return var_name;
    }

    public void setVar_name(String var_name) {
        this.var_name = var_name;
    }

    public Expression getExpr() {
        return expr;
    }

    public void setExpr(Expression expr) {
        this.expr = expr;
    }

    @Override
    public PrgState execute(PrgState state) {
        int address = IDGenerator.getID();
        IDictionary<String, Integer> dict = state.getSymbolTable();
        if(dict.exists(var_name))
            dict.setValue(var_name, address);
        else
            dict.add(var_name, address);
        int val = expr.eval(state.getSymbolTable(), state.getHeap());
        state.getHeap().add(address, val);
        return null;
    }

    @Override
    public String toString() {
        return "New(" + var_name + ", " + expr + ")";
    }
}
