package model.statement;

import model.PrgState;
import model.expression.Expression;

public class IfStatement implements Statement {
    private Expression e;
    private Statement than, els;

    public IfStatement(Expression e, Statement than, Statement els) {
        this.e = e;
        this.than = than;
        this.els = els;
    }

    @Override
    public PrgState execute(PrgState state) {
        int val = e.eval(state.getSymbolTable(), state.getHeap());
        if(val != 0){
            state.getExecStack().push(than);
        }
        else{
            state.getExecStack().push(els);
        }
        return null;
    }
    @Override
    public String toString(){
        return "if(" + e + ") then \n" + than + "\n else \n" + els;
    }
}
