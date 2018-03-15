package model.statement;

import model.PrgState;
import model.expression.Expression;

public class WhileStatement implements Statement {
    private Expression expression;
    private Statement statement;

    public WhileStatement(Expression expression, Statement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        int val = expression.eval(state.getSymbolTable(), state.getHeap());
        if(val == 0)
            return null;
        state.getExecStack().push(this);
        state.getExecStack().push(statement);
        return null;
    }

    @Override
    public String toString(){
        return "while(" + expression + "){\n" + statement + "\n}\n";
    }
}
