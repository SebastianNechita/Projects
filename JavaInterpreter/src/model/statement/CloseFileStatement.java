package model.statement;

import exceptions.InterpreterException;
import model.PrgState;
import model.expression.Expression;

import java.io.IOException;

public class CloseFileStatement implements Statement {
    private Expression e;

    public CloseFileStatement(Expression e) {
        this.e = e;
    }

    @Override
    public PrgState execute(PrgState state) {
        int val = e.eval(state.getSymbolTable(), state.getHeap());
        if(!state.getFileTable().contains(val))
            throw new InterpreterException("Could not close the file\n");
        try{
            state.getFileTable().get(val).getFileDescriptor().close();
            state.getFileTable().remove(val);
        }catch (IOException e){
            throw new InterpreterException("Could not close the file\n");
        }
        return null;
    }

    @Override
    public String toString() {
        return "closeFile(" + e + ")";
    }
}
