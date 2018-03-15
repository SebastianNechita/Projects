package model.dateStructures;

import java.util.*;

public class MyList<I> implements IList<I> {
    private List<I> l;

    public MyList() {
        l = new ArrayList<>();
    }

    @Override
    public void add(I elem) {
        l.add(elem);
    }

    @Override
    public String toString(){
        StringBuffer str = new StringBuffer();
        for(I e : l){
            str.append(e);
            str.append(";");
        }
        return str.toString();
    }

    @Override
    public Iterable<I> getAll() {
        return l;
    }
}
