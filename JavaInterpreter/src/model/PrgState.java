package model;

import exceptions.InterpreterException;
import model.dateStructures.*;
import model.statement.Statement;

public class PrgState {
    private static int IDStatic = 0;
    private int id;
    private IStack<IDictionary<String, Integer> > symbolTable;
    private IStack<Statement> execStack;
    private IProcTable<String, ProcData> procTable;
    private IList<Integer> output;
    private Statement rootP;
    private FileTable<Integer, FileData> fileTable;
    private IHeap<Integer, Integer> heap;

    public PrgState(IStack<IDictionary<String, Integer>> symbolTable, IStack<Statement> execStack, IList<Integer> output, Statement rootP, FileTable<Integer, FileData> fileTable, IHeap<Integer, Integer> heap, IProcTable<String, ProcData> procTable) {
        this.id = IDStatic++;
        this.symbolTable = symbolTable;
        this.execStack = execStack;
        this.execStack.push(rootP);
        this.output = output;
        this.rootP = rootP;
        this.fileTable = fileTable;
        this.heap = heap;
        this.procTable = procTable;
    }

    public IProcTable<String, ProcData> getProcTable() {
        return procTable;
    }

    public void setProcTable(IProcTable<String, ProcData> procTable) {
        this.procTable = procTable;
    }

    public IDictionary<String, Integer> getSymbolTable() {
        return symbolTable.top();
    }
    public IStack<IDictionary<String, Integer>> getAllSymbolTable() {
        return symbolTable;
    }

    public void setAllSymbolTable(IStack<IDictionary<String, Integer>> symbolTable) {
        this.symbolTable = symbolTable;
    }

    public IStack<Statement> getExecStack() {
        return execStack;
    }

    public void setExecStack(IStack<Statement> execStack) {
        this.execStack = execStack;
    }

    public Statement getRootP() {
        return rootP;
    }

    public void setRootP(Statement rootP) {
        this.rootP = rootP;
    }

    public IList<Integer> getOutput() {
        return output;
    }

    public void setOutput(IList<Integer> output) {
        this.output = output;
    }

    public FileTable<Integer, FileData> getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTable<Integer, FileData> fileTable) {
        this.fileTable = fileTable;
    }

    public IHeap<Integer, Integer> getHeap() {
        return heap;
    }

    public void setHeap(IHeap<Integer, Integer> heap) {
        this.heap = heap;
    }

    public boolean isNotComplete(){return !execStack.isEmpty();};

    public PrgState executeOneStep(){
        if (getExecStack().isEmpty())
            throw new InterpreterException("Program terminated\n");
        Statement s = getExecStack().pop();
        return s.execute(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        str.append("PrgState{\n");
        str.append("ID={");
        str.append(id);
        str.append("}");
        str.append("symbolTable={");
        str.append(symbolTable);
        str.append("}, \nexecStack={");
        str.append(execStack);
        str.append("}, \noutput={");
        str.append(output);
        str.append("}, \nrootP={");
        str.append(rootP);
        str.append("}, \nfileTable={");
        str.append(fileTable);
        str.append("}, \nheap={");
        str.append(heap);
        str.append("}}\n");
        return str.toString();
    }
}
