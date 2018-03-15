package sample;

import controller.Repository;
import exceptions.InterpreterException;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ListPropertyBase;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.FileData;
import model.PrgState;
import model.ProcData;
import model.StringPair;
import model.dateStructures.*;
import model.expression.ArithmeticExpression;
import model.expression.VarExpression;
import model.statement.AsignmentStatement;
import model.statement.CompStatement;
import model.statement.PrintStatement;
import model.statement.Statement;

import javax.swing.plaf.nimbus.State;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Controller2ndScene {
    private Statement statement;
    private Repository repo;
    private ExecutorService executor;
    private List<PrgState> prgs;

    public IProcTable<String, ProcData> getAllProcedures(){
        IProcTable<String, ProcData> procTable = new ProcTable<>();
        ArrayList<String> lis = new ArrayList<>();
        lis.add("a");
        lis.add("b");
        addProcedure(procTable, "sum", lis, new CompStatement(
                new AsignmentStatement("v", new ArithmeticExpression(new VarExpression("a"), new VarExpression("b"), '+')),
                new PrintStatement(new VarExpression("v"))
        ));
        addProcedure(procTable, "product", lis, new CompStatement(
                new AsignmentStatement("v", new ArithmeticExpression(new VarExpression("a"), new VarExpression("b"), '*')),
                new PrintStatement(new VarExpression("v"))
        ));
        return procTable;
    }

    public Controller2ndScene(Statement s){
        executor = Executors.newFixedThreadPool(4);
        statement = s;
        repo = new Repository("logFile.txt");
        IStack<IDictionary<String, Integer>> stack = new MyStack<>();
        IDictionary<String, Integer> dict = new MyDictionary<>();
        stack.push(dict);
        repo.addProgramState(
                new PrgState(stack, new MyStack<>(), new MyList<>(), statement, new FileTable<>(), new Heap<>(), getAllProcedures())
        );
        prgs = removeCompletedPrg(repo.getPrgList());
        prgs.forEach(p -> repo.logPrgStateExec(p));
    }
    public void addProcedure(IProcTable<String, ProcData> procTable, String procName, List<String> parameters, Statement statement){
        procTable.add(procName, new ProcData(parameters, statement));
    }
    public void initialize(){
        updateData();
        prgStatesList.setOnMouseClicked(x->updateData());
        heapTableAddress.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        heapTableValue.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
        fileTableIdentifier.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        fileTableFileName.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
        symbolTableVariable.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        symbolTableValue.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
        procedureColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("first"));
        bodyColumn.setCellValueFactory(new PropertyValueFactory<StringPair, String>("second"));
    }

    @FXML
    private TextField nrOfProgramStates;

    @FXML
    private TableView<StringPair> heapTable;

    @FXML
    private TableColumn<StringPair, String> heapTableAddress;

    @FXML
    private TableColumn<StringPair, String> heapTableValue;

    @FXML
    private ListView<String> outList;

    @FXML
    private TableView<StringPair> fileTable;

    @FXML
    private TableColumn<StringPair, String> fileTableIdentifier;

    @FXML
    private TableColumn<StringPair, String> fileTableFileName;

    @FXML
    private ListView<Integer> prgStatesList;

    @FXML
    private TableView<StringPair> symbolTable;

    @FXML
    private TableColumn<StringPair, String> symbolTableVariable;

    @FXML
    private TableColumn<StringPair, String> symbolTableValue;

    @FXML
    private TableView<StringPair> procTable;

    @FXML
    private TableColumn<StringPair, String> procedureColumn;

    @FXML
    private TableColumn<StringPair, String> bodyColumn;

    @FXML
    private ListView<String> executionStackTable;

    @FXML
    private Button button;

    @FXML
    private Button buttonAll;

    @FXML
    void allButtonPushed(ActionEvent event) {
        long nr;
        do {
            buttonClicked(event);
            nr = repo.getPrgList().stream().filter(p -> !p.getExecStack().isEmpty()).count();
        }while(nr > 0);
    }
    @FXML
    void buttonClicked(ActionEvent event) {
        try{
            if(prgs.size() > 0) {
                prgs = removeCompletedPrg(repo.getPrgList());
                oneStepForAllPrg(prgs);
                repo.setPrgList(prgs);
            }
        } catch (Exception e) {
            Alert a = new Alert(Alert.AlertType.ERROR, e.getMessage());
            a.show();
            Stage s = (Stage)button.getScene().getWindow();
            s.close();
        }
       updateData();

    }

    Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTable, IHeap<Integer, Integer> heap){
        return heap.getAll().stream()
                .filter(entry -> symTable.contains(entry.getKey()))
                .collect(Collectors.toMap(entry->entry.getKey(), entry->entry.getValue()));
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> l){
        return l.stream().filter(p -> p.isNotComplete()).collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> programs){
        List<Callable<PrgState>> callList = programs
                .stream()
                .map((PrgState p) -> (Callable<PrgState>)(()->p.executeOneStep()))
                .collect(Collectors.toList());
        try {
            List<PrgState> newPrgList = executor.invokeAll(callList)
                    .stream()
                    .map(future -> {try{
                        return future.get();
                    }catch (Exception e){
                        throw new InterpreterException(e.getMessage());
                    }
                    })
                    .filter(p -> p!=null)
                    .collect(Collectors.toList());
            System.out.println("The number of new programs is " + newPrgList.size());
            programs.addAll(newPrgList);
            System.out.println("The number of programs is " + programs.size());


        } catch (InterruptedException e) {
            throw new InterpreterException("Threads interrupted while running\n");
        }
        programs.forEach(p -> repo.logPrgStateExec(p));
        repo.setPrgList(programs);
        System.out.println(repo.getPrgList().size());
    }

    private void updateExecStack(PrgState state){
        ObservableList<String> li = FXCollections.observableArrayList();
        for(Statement s : state.getExecStack().getAll())
            li.add(s.toString());
        executionStackTable.setItems(li);
    }

    private void updateOutput(PrgState state){
        ObservableList<String> li = FXCollections.observableArrayList();
        for(Integer i : state.getOutput().getAll())
            li.add(i.toString());
        outList.setItems(li);
    }

    private void updateSymbolTable(PrgState state){
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<String, Integer> entry : state.getSymbolTable().getAll()){
            li.add(new StringPair(entry.getKey(), entry.getValue().toString()));
        }
        symbolTable.setItems(li);
    }

    private void updateFileTable(PrgState state){
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<Integer, FileData> entry : state.getFileTable().getAll()){
            li.add(new StringPair(entry.getKey().toString(), entry.getValue().getFileName().toString()));
        }
        fileTable.setItems(li);
    }

    private void updateHeap(PrgState state){
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<Integer, Integer> entry : state.getHeap().getAll()){
            li.add(new StringPair(entry.getKey().toString(), entry.getValue().toString()));
        }
        heapTable.setItems(li);
    }

    private void updateProcTable(PrgState state){
        ObservableList<StringPair> li = FXCollections.observableArrayList();
        for(Map.Entry<String, ProcData> entry : state.getProcTable().getAll()){
            li.add(new StringPair(entry.getKey() + entry.getValue().getParameters(), "" + entry.getValue().getBody()));
        }
        procTable.setItems(li);
    }

    private void updateData(){
        Integer id = updatePrgStates();
        List<PrgState> l = repo.getPrgList().stream().filter(x -> x.getId() == id).collect(Collectors.toList());
        if(l.size() == 0){
            executionStackTable.setItems(FXCollections.observableArrayList());
            outList.setItems(FXCollections.observableArrayList());
            symbolTable.setItems(FXCollections.observableArrayList());
            heapTable.setItems(FXCollections.observableArrayList());
            fileTable.setItems(FXCollections.observableArrayList());
            System.out.println("The selected id does not exist");
            return;
        }

        PrgState state = l.get(0);
        updateExecStack(state);
        updateOutput(state);
        updateSymbolTable(state);
        updateFileTable(state);
        updateHeap(state);
        updateProcTable(state);
        System.out.println("!!!!!!!!!!!!" + state.getProcTable().getAll().size());

        nrOfProgramStates.setText("Nr of program states: " + repo.getPrgList().size());


    }

    private Integer updatePrgStates(){
        ObservableList<Integer> l = prgStatesList.getSelectionModel().getSelectedIndices();
        Integer id = null;
            if(l.size() == 1) id = prgStatesList.getItems().get(l.get(0));
            Integer newid = 0;
            ObservableList<Integer> newlist = FXCollections.observableArrayList();
            for(PrgState state : repo.getPrgList()) {
                if (id != null && id == state.getId()) {
                    newid = newlist.size();
                }
                newlist.add(state.getId());
        }
        prgStatesList.setItems(newlist);
        if(newlist.size() > newid){
            prgStatesList.getSelectionModel().select(newid);
            return newlist.get(newid);
        }
        return null;
    }
    private Integer updatePrgStates2(){
        int index = prgStatesList.getSelectionModel().getSelectedIndex();
        System.out.println(index);
        if(index == -1 && prgStatesList.getItems().size() > 0){
            prgStatesList.getSelectionModel().select(0);
            index = 0;
        }
        if(index >= 0){
            return prgStatesList.getItems().get(index);
        }
        return null;
    }
}