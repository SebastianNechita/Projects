package controller;

import exceptions.InterpreterException;
import model.FileData;
import model.PrgState;
import model.ProcData;
import model.dateStructures.*;
import model.statement.Statement;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    private String fileName;

    public Repository(String fileName) {
        prgStates = new ArrayList<>();
        this.fileName = fileName;
        try(PrintWriter log = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)))){

        }catch (IOException e){
            throw new InterpreterException("Could not open log file");
        }
    }
    @Override
    public void addProgramState(PrgState st){
        prgStates.add(st);
    }

    @Override
    public List<PrgState> getPrgList() {
        return prgStates;
    }

    @Override
    public void setPrgList(List<PrgState> l) {
        prgStates = l;
    }

    @Override
    public void logPrgStateExec(PrgState state) {
        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))){
            logFile.println("PRGSTATE ID:" + state.getId());
            logFile.println("ExeStack:");
            IStack<Statement> exeStack = state.getExecStack();
            for(Statement s : exeStack.getAll()){
                logFile.println(s);
            }
            logFile.println("SymTable:");
            IDictionary<String, Integer> symTable = state.getSymbolTable();
            for(Map.Entry<String, Integer> entry : symTable.getAll()){
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println("Out:");
            IList<Integer> l = state.getOutput();
            for(Integer i : l.getAll()){
                logFile.println(i);
            }
            logFile.println("FileTable:");
            IFileTable<Integer, FileData> f = state.getFileTable();
            for(Map.Entry<Integer, FileData> entry : f.getAll()){
                logFile.println(entry.getKey() + "-->" + entry.getValue().getFileName());
            }
            logFile.println("Heap:");
            IHeap<Integer, Integer> h = state.getHeap();
            for(Map.Entry<Integer, Integer> entry : h.getAll()){
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println("ProcTable:");
            IProcTable<String, ProcData> p = state.getProcTable();
            for(Map.Entry<String, ProcData> entry : p.getAll()){
                logFile.println(entry.getKey() + "-->" + entry.getValue());
            }
            logFile.println("\n");
        }catch(IOException e){
            throw new InterpreterException("Could not open log file");
        }
    }
}
