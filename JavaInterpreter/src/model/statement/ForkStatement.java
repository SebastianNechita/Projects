package model.statement;

import model.PrgState;
import model.dateStructures.IDictionary;
import model.dateStructures.IStack;
import model.dateStructures.MyStack;

public class ForkStatement implements Statement {
    private Statement statement;

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public ForkStatement(Statement statement) {

        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        IStack<Statement> newStack = new MyStack<>();
        IStack<IDictionary<String, Integer>> symTable = new MyStack<>();
        for(IDictionary<String, Integer> val : state.getAllSymbolTable().getAll()){
            symTable.push(val.clone());
        }
        IStack<IDictionary<String, Integer>> realSymTable = new MyStack<>();
        for(IDictionary<String, Integer> val : symTable.getAll()){
            realSymTable.push(val.clone());
        }

        PrgState newState = new PrgState(realSymTable, newStack, state.getOutput(), statement, state.getFileTable(), state.getHeap(), state.getProcTable());
        return newState;
    }

    @Override
    public String toString(){
        return "Fork(\n" + statement + "\n)";
    }
}
