package model.statement;

import model.PrgState;

public class CompStatement implements Statement {
    private Statement first;
    private Statement second;

    public CompStatement(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public PrgState execute(PrgState state) {
        state.getExecStack().push(second);
        state.getExecStack().push(first);
        return null;
    }

    @Override
    public String toString() {
        return first + ";\n" + second;
    }
}
