package sample;

import exceptions.InterpreterException;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.ProcData;
import model.dateStructures.IProcTable;
import model.expression.*;
import model.statement.*;
import java.io.IOException;
import java.util.*;

public class Controller {

    @FXML
    private HBox hbox;

    @FXML
    private ListView<String> listView;

    private static Statement makeStatement(Statement[] l){
        Statement s = l[0];
        for(int i = 1 ; i < l.length ; i++){
            s = new CompStatement(s, l[i]);
        }
        return s;
    }

    public List<Statement> getAllStatements(){
        int nr = 1;
        List<Statement> s = new ArrayList<>();
        s.add(new CompStatement(
                 new AsignmentStatement("sebi", new ConstExpression(1)),
                 new PrintStatement(new VarExpression("sei"))));
        s.add(new CompStatement(
                new AsignmentStatement("a",
                        new ArithmeticExpression(
                                new ConstExpression(2),
                                new ArithmeticExpression(
                                        new ConstExpression(3),
                                        new ConstExpression(5), '*'),
                                        '+')),
                new CompStatement(
                        new AsignmentStatement(
                                "b",
                                new ArithmeticExpression(
                                        new VarExpression("a"),
                                        new ConstExpression(1),
                                        '+')),
                        new PrintStatement(new VarExpression("b")))));
        s.add(new CompStatement(
                        new AsignmentStatement("a", new ArithmeticExpression(new ConstExpression(2), new ConstExpression(2), '-')),
                        new CompStatement(
                                new IfStatement(
                                        new VarExpression("a"),
                                        new AsignmentStatement(
                                                "v",
                                                new ConstExpression(2)
                                        ),
                                        new AsignmentStatement(
                                                "v",
                                                new ConstExpression(3)
                                        )
                                ),
                                new PrintStatement(new VarExpression("v"))
                        )
                ));
        s.add(new CompStatement(
                        new CompStatement(
                                new OpenRFileStatement("a", "a.txt"),
                                new ReadFileStatement(
                                        new VarExpression("a"),
                                        "b")
                                ),
                        new CompStatement(
                                new PrintStatement(new VarExpression("b")),
                                new CloseFileStatement(new VarExpression("a"))
                        )
                ));
        s.add(new CompStatement(
                        new CompStatement(
                                new OpenRFileStatement("var_f", "test.in"),
                                new CompStatement(
                                        new ReadFileStatement(new VarExpression("var_f"), "var_c"),
                                        new PrintStatement(new VarExpression("var_c")))),
                        new CompStatement(
                                new IfStatement(
                                        new VarExpression("var_c"),
                                        new CompStatement(
                                                new ReadFileStatement(new VarExpression("var_f"), "var_c"),
                                                new PrintStatement(new VarExpression("var_c"))),

                                        new PrintStatement(new ConstExpression(0))),
                                new CloseFileStatement(new VarExpression("var_f")))
                ));
        s.add(new CompStatement(
                        new CompStatement(
                                new OpenRFileStatement("var_f", "test.in"),
                                new CompStatement(
                                        new ReadFileStatement(
                                                new ArithmeticExpression(
                                                        new VarExpression("var_f"),
                                                        new ConstExpression(2),
                                                        '+'),
                                               "var_c"),
                                        new PrintStatement(new VarExpression("var_c")))),
                        new CompStatement(
                                new IfStatement(
                                        new VarExpression("var_c"),
                                        new CompStatement(
                                                new ReadFileStatement(new VarExpression("var_f"), "var_c"),
                                                new PrintStatement(new VarExpression("var_c"))),
                                        new PrintStatement(new ConstExpression(0))),
                                new CloseFileStatement(new VarExpression("var_f")))
                ));
        s.add(new CompStatement(
                        new CompStatement(
                                new CompStatement(
                                        new AsignmentStatement(
                                                "v",
                                                new ConstExpression(10)
                                        ),
                                        new NewHStatement(
                                                "v",
                                                new ConstExpression(20)
                                        )
                                ),
                                new NewHStatement(
                                        "a",
                                        new ConstExpression(22)
                                )
                        ),
                        new CompStatement(
                                new PrintStatement(
                                        new ArithmeticExpression(
                                                new ConstExpression(100),
                                                new ReadExpression("v"),
                                                '+'
                                        )
                                ),
                                new PrintStatement(
                                        new ArithmeticExpression(
                                                new ConstExpression(100),
                                                new ReadExpression("a"),
                                                '+'
                                        )
                                )
                        )
                ));
        Statement stati[] = new Statement[]{
                new AsignmentStatement("v",new ConstExpression(10)),
                new NewHStatement("v",new ConstExpression(20)),
                new NewHStatement("a", new ConstExpression(22)),
                new WriteStatement("a", new ConstExpression(30)),
                new PrintStatement(new VarExpression("a")),
                new PrintStatement(new ReadExpression("a"))
        };
        s.add(makeStatement(stati));
        Statement stati2[] = {
                new AsignmentStatement("v",new ConstExpression(10)),
                new NewHStatement("v",new ConstExpression(20)),
                new NewHStatement("a", new ConstExpression(22)),
                new WriteStatement("a", new ConstExpression(30)),
                new PrintStatement(new VarExpression("a")),
                new PrintStatement(new ReadExpression("a")),
                new AsignmentStatement("a", new ConstExpression(0))
        };
        s.add(makeStatement(stati2));

        s.add(new PrintStatement(new ArithmeticExpression(
                new ConstExpression(10),
                new BooleanExpression(
                        new ConstExpression(2),
                        new ConstExpression(6),
                        "<"
                ),
                '+'
        )));
        s.add(new PrintStatement(new BooleanExpression(
                new ArithmeticExpression(
                        new ConstExpression(10),
                        new ConstExpression(2),
                        '+'
                ),
                new ConstExpression(6),
                "<"
        )));

        Statement[] stati3 = {
                new AsignmentStatement("v", new ConstExpression(6)),
                new WhileStatement(
                        new ArithmeticExpression(
                                new VarExpression("v"),
                                new ConstExpression(4),
                                '-'
                        ),
                        new CompStatement(
                                new PrintStatement(new VarExpression("v")),
                                new AsignmentStatement(
                                        "v",
                                        new ArithmeticExpression(
                                                new VarExpression("v"),
                                                new ConstExpression(1),
                                                '-'
                                        )
                                )
                        )
                ),
                new PrintStatement(new VarExpression("v"))
        };
        s.add(makeStatement(stati3));

        s.add(new OpenRFileStatement("a", "a.txt"));
        Statement[] forkStatements = {
            new WriteStatement("a", new ConstExpression(30)),
            new AsignmentStatement("v", new ConstExpression(32)),
            new PrintStatement(new VarExpression("v")),
            new PrintStatement(new ReadExpression("a"))
        };
        Statement[] stati4 = {
                new AsignmentStatement("v" , new ConstExpression(10)),
                new NewHStatement("a", new ConstExpression(22)),
                new ForkStatement(makeStatement(forkStatements)),
                new PrintStatement(new VarExpression("v")),
                new PrintStatement(new ReadExpression("a"))
        };
        s.add(makeStatement(stati4));//Fork Statement
        //v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        Statement ex5 = new CompStatement(new AsignmentStatement("v",new ConstExpression(10)),
                new CompStatement(new NewHStatement("a",new ConstExpression(22)),
                        new CompStatement(new ForkStatement(new CompStatement(new WriteStatement("a",new ConstExpression(30)),
                                new CompStatement(new AsignmentStatement ("v",new ConstExpression(32)),
                                        new CompStatement(new PrintStatement(new VarExpression("v")),
                                                new PrintStatement(new ReadExpression("a")))))),
                                new CompStatement(new PrintStatement(new VarExpression("v")),
                                        new PrintStatement(new ReadExpression("a"))))));;
        s.add(ex5);
        //n=100
        //i=2
        //while(i<=n){
        //  j=2
        //
        //  while(j<i){
        //      if(i-i/j*j)
        //          j=i+1
        //      else
        //          j=j+1
        //  }
        //  if(j-i)
        //      print(0)
        //  else
        //      print(i)
        //  i=i+1
        //}

        Expression aritmetic =
                 new ArithmeticExpression(
                        new VarExpression("i"),
                        new ArithmeticExpression(
                                new ArithmeticExpression(
                                        new VarExpression("i"),
                                        new VarExpression("j"),
                                        '/'),
                        new VarExpression("j"),
                        '*'),
                '-');
        Statement ifStatement = new IfStatement(aritmetic,
                                                    new AsignmentStatement("j", new ArithmeticExpression(new VarExpression("j"), new ConstExpression(1), '+')),
                                                    new AsignmentStatement("j", new ArithmeticExpression(new VarExpression("i"), new ConstExpression(1), '+'))
                                            );
        Statement s1 = new NewHStatement("n", new ConstExpression(50));
        Statement s2 = new AsignmentStatement("i", new ConstExpression(2));
        Statement s3 = new WhileStatement(new BooleanExpression(new VarExpression("i"), new ReadExpression("n"), "<="),
                makeStatement(new Statement[]{
                            new ForkStatement(
                                    makeStatement(new Statement[]{
                                            new AsignmentStatement("j", new ConstExpression(2)),
                                            new WhileStatement(new BooleanExpression(new VarExpression("j"), new VarExpression("i"), "<"), ifStatement),
                                            new IfStatement(
                                                    new ArithmeticExpression(new VarExpression("j"), new VarExpression("i"), '-'),
                                                    new AsignmentStatement("n", new VarExpression("n")),
                                                    new PrintStatement(new VarExpression("i"))
                            )}))
                            ,
                            new AsignmentStatement("i", new ArithmeticExpression(new VarExpression("i"), new ConstExpression(1), '+'))
                        })
        );
        s.add(makeStatement(new Statement[]{s1, s2, s3}));

        List<Expression> l1 = new ArrayList<>();
        l1.add(new ArithmeticExpression(new VarExpression("v"), new ConstExpression(10), '*'));
        l1.add(new VarExpression("w"));
        List<Expression> l2 = new ArrayList<>();
        l2.add(new VarExpression("v"));
        l2.add(new VarExpression("w"));
        List<Expression> l3 = new ArrayList<>();
        l3.add(new VarExpression("v"));
        l3.add(new VarExpression("w"));
        s.add(makeStatement(new Statement[]{
                new AsignmentStatement("v", new ConstExpression(2)),
                new AsignmentStatement("w", new ConstExpression(5)),
                new CallStatement("sum", l1),
                new PrintStatement(new VarExpression("v")),
                new ForkStatement(new CompStatement(
                        new CallStatement("product", l2),
                        new ForkStatement(
                                new CallStatement("sum", l3)
                        )
                ))
        }));



        return s;

    }

    public void initialize(){
        List<Statement> statements = getAllStatements();
        int nr = 0;
        for(Statement st : statements){
            listView.getItems().add("" + (nr++) + ". " + st.toString());
        }
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               ObservableList<Integer> l = listView.getSelectionModel().getSelectedIndices();
               if(l.size() == 1){
                   System.out.println("Running statement number " + l.get(0));
                   createNewRun(statements.get(l.get(0)));
               }
            }
        });
    }

    public void createNewRun(Statement s){
        Stage stage = new Stage();
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("second.fxml"));
            loader.setController(new Controller2ndScene(s));
            root = loader.load();
            stage.setTitle("Muhahahaha");

            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e){
            System.out.println(e.getMessage());
            throw new InterpreterException(e.getMessage());
        }
    }
}
