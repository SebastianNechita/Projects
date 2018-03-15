package model.statement;

import model.PrgState;

public interface Statement {
    public PrgState execute(PrgState state);
}
