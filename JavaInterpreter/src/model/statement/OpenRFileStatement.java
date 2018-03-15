package model.statement;

import exceptions.InterpreterException;
import model.FileData;
import model.IDGenerator;
import model.PrgState;
import model.dateStructures.FileTable;
import model.dateStructures.IDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class OpenRFileStatement implements Statement {
    private String variable;
    private String fileName;

    public OpenRFileStatement(String variable, String fileName) {
        this.variable = variable;
        this.fileName = fileName;
    }

    @Override
    public PrgState execute(PrgState state) {
        FileTable<Integer, FileData> fileTable = state.getFileTable();
        if(fileIsOpen(state))
            throw new InterpreterException("The file is already open\n");
        try{
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            int id = IDGenerator.getID();
            fileTable.add(id, new FileData(fileName, bf));
            IDictionary<String, Integer> symTable = state.getSymbolTable();
            if(symTable.exists(variable))
                symTable.setValue(variable, id);
            else
                symTable.add(variable, id);

        }catch(IOException e){
            throw new InterpreterException("The file could not be opened\n");
        }


        return null;
    }

    private boolean fileIsOpen(PrgState state) {
        for(Map.Entry<Integer, FileData> e : state.getFileTable().getAll()){
            if(e.getValue().getFileName().equals(fileName))
                return true;
        }
        return false;
    }

    @Override
    public String toString(){
        return "openFile(" + variable + ", " + fileName + ")";
    }
}
