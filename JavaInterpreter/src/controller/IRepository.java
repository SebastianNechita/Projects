package controller;

import model.PrgState;

import java.util.List;

public interface IRepository {
    public void addProgramState(PrgState st);
    public void logPrgStateExec(PrgState state);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> l);
}
