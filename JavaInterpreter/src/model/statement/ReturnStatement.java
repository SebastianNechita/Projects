package model.statement;

import model.PrgState;

public class ReturnStatement implements Statement {
    @Override
    public PrgState execute(PrgState state) {
        state.getAllSymbolTable().pop();
        return null;
    }

    @Override
    public String toString() {
        return "Return";
    }
}
