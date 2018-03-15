package model.dateStructures;

import exceptions.InterpreterException;

import java.util.ArrayDeque;
import java.util.Deque;

public class MyStack<T> implements IStack<T> {
    private Deque<T> st;

    public MyStack() {
        st = new ArrayDeque<>();
    }

    @Override
    public void push(T elem) {
        st.push(elem);
    }

    @Override
    public T pop() {
        if(st.isEmpty())
            throw new InterpreterException("The stack is empty!\n");
        return st.pop();
    }

    @Override
    public boolean isEmpty() {
        return st.isEmpty();
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for(T elem : st){
            str.append(elem);
            str.append("\n-----------------------\n");
        }
        return str.toString();
    }

    @Override
    public T top() {
        if(st.size() == 0){
            throw new InterpreterException("Stack is empty");
        }
        return st.getFirst();
    }

    @Override
    public Iterable<T> getAll() {
        return st;
    }
}
