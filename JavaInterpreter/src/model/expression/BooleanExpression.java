package model.expression;

import exceptions.InterpreterException;
import model.dateStructures.IDictionary;
import model.dateStructures.IHeap;
import model.expression.Expression;

public class BooleanExpression implements Expression{
    private Expression left, right;
    private String relation;

    public BooleanExpression(Expression left, Expression right, String relation) {
        this.left = left;
        this.right = right;
        this.relation = relation;
    }

    public Expression getLeft() {

        return left;
    }

    public void setLeft(Expression left) {
        this.left = left;
    }

    public Expression getRight() {
        return right;
    }

    public void setRight(Expression right) {
        this.right = right;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public int eval(IDictionary<String, Integer> dict, IHeap<Integer, Integer> heap) {
        int val1 = left.eval(dict, heap);
        int val2 = right.eval(dict, heap);
        boolean result;
        if(relation.equals("<")){ result = (val1 < val2); }
        else if(relation.equals("<=")){ result = (val1 <= val2); }
        else if(relation.equals("!=")){ result = (val1 != val2); }
        else if(relation.equals("==")){ result = (val1 == val2); }
        else if(relation.equals(">")){ result = (val1 > val2); }
        else if(relation.equals(">=")){ result = (val1 >= val2); }
        else throw new InterpreterException("No such operator as " + relation);
        if(result)
            return 1;
        return 0;
    }

    @Override
    public String toString(){
        return "(" + left + " " + relation + " " + right + ")";
    }
}
