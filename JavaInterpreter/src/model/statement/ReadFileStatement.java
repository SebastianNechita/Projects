package model.statement;

import exceptions.InterpreterException;
import model.FileData;
import model.PrgState;
import model.dateStructures.FileTable;
import model.dateStructures.IDictionary;
import model.expression.Expression;

import java.io.IOException;

public class ReadFileStatement implements Statement{
    private Expression e;
    private String name;

    public ReadFileStatement(Expression e, String name) {
        this.e = e;
        this.name = name;
    }

    @Override
    public PrgState execute(PrgState state) {
        int value = e.eval(state.getSymbolTable(), state.getHeap());
        FileTable<Integer, FileData> fileTable = state.getFileTable();
        if(!fileTable.contains(value))
            throw new InterpreterException("No such file descriptor as " + value);
        try{
            int ret;
            System.out.println(fileTable.get(value).getFileDescriptor());
            String line = fileTable.get(value).getFileDescriptor().readLine();
            System.out.println("I have read " + line);
            if(line == null){
                ret = 0;
                System.out.println("Ieeei it is null\n");
            }
            else if(line.equals(""))
                ret = 0;
            else
                ret = Integer.parseInt(line);
            IDictionary<String, Integer> symbolTable = state.getSymbolTable();
            if(symbolTable.exists(name))
                symbolTable.setValue(name, ret);
            else
                symbolTable.add(name, ret);
        }catch(IOException e){
            System.out.println(e.toString());
            throw new InterpreterException("Could not read the file " + fileTable.get(value).getFileName());
        }

        return null;
    }

    @Override
    public String toString(){
        return "readFile(" + e + ", " + name + ")";
    }
}
