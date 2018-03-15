package model.dateStructures;

public interface IStack<T>{
     public void push(T elem);
     public T pop();
     public boolean isEmpty();
     public Iterable<T> getAll();
     public T top();
}
