package model.statement;

import exceptions.InterpreterException;
import model.PrgState;
import model.ProcData;
import model.dateStructures.IDictionary;
import model.dateStructures.MyDictionary;
import model.expression.Expression;

import java.util.ArrayList;
import java.util.List;

public class CallStatement implements Statement {
    private String procName;
    private List<Expression> parameters;

    public CallStatement(String procName, List<Expression> parameters) {
        this.procName = procName;
        this.parameters = parameters;
    }

    @Override
    public PrgState execute(PrgState state) {
        if(!state.getProcTable().contains(procName))
            throw new InterpreterException("That procedure does not exist");
        ProcData p = state.getProcTable().get(procName);
        if(p.getParameters().size() != parameters.size())
            throw new InterpreterException("Wrong number of parameters for the procedure");
        List<Integer> values = new ArrayList<>();
        for(Expression e : parameters){
            values.add(e.eval(state.getSymbolTable(), state.getHeap()));
        }
        IDictionary<String, Integer> dict = new MyDictionary<>();
        for(int i = 0 ; i < values.size() ; i++){
            dict.add(p.getParameters().get(i), values.get(i));
        }
        state.getAllSymbolTable().push(dict);
        state.getExecStack().push(new ReturnStatement());
        state.getExecStack().push(p.getBody());
        return null;
    }

    public String getProcName() {
        return procName;
    }

    public void setProcName(String procName) {
        this.procName = procName;
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    public void setParameters(List<Expression> parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "Call " + procName + parameters;
    }
}
