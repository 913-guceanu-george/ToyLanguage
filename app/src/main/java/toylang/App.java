package toylang;

import java.io.BufferedReader;

import controller.Controller;
import model.expressions.ArithemticExpression;
import model.expressions.ReadHeapExpression;
import model.expressions.RelationalExpression;
import model.expressions.ValueExpression;
import model.expressions.VariableExpression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.Dict;
import model.prgstate.datastructures.dictionary.IDict;
import model.prgstate.datastructures.list.IMList;
import model.prgstate.datastructures.list.MList;
import model.prgstate.datastructures.stack.IStack;
import model.prgstate.datastructures.stack.Stack;
import model.statements.*;
import model.symbol.value.IntValue;
import model.symbol.value.StringValue;
import model.symbol.value.Value;
import repository.IRepository;
import repository.Repository;
import view.View;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void newProgram(CompStmt program) {
        IRepository repo = new Repository();
        Controller ctrl = new Controller(repo);
        View view = new View(ctrl);

        IDict<String, Value> symTable = new Dict<>();
        IMList<Value> out = new MList<>();
        IStack<IStmt> exeStack = new Stack<>();
        IDict<Integer, Value> heap = new Dict<>();

        PrgState prgState = new PrgState(symTable, exeStack, out, heap, program);
        view.addPrgState(prgState);
        view.run();
    }

    public static CompStmt program1() {
        CompStmt program1 = new CompStmt(new VarDecl("v", "int"),
                new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(2))),
                        new PrintStmt(new VariableExpression("v"))));
        return program1;
    }

    public static CompStmt program2() {
        CompStmt program2 = new CompStmt(new VarDecl("a", "int"),
                new CompStmt(new VarDecl("b", "int"),
                        new CompStmt(
                                new AssignStmt("a",
                                        new ArithemticExpression(new ValueExpression(new IntValue(2)),
                                                new ArithemticExpression(new ValueExpression(new IntValue(3)),
                                                        new ValueExpression(new IntValue(5)), '*'),
                                                '+')),
                                new CompStmt(
                                        new AssignStmt("b",
                                                new ArithemticExpression(new VariableExpression("a"),
                                                        new ValueExpression(new IntValue(1)), '+')),
                                        new PrintStmt(new VariableExpression("b"))))));
        return program2;
    }

    public static CompStmt program3() {
        VarDecl aDecl = new VarDecl("a", "string");
        VarDecl bDecl = new VarDecl("b", "int");
        AssignStmt aAssign = new AssignStmt("a", new ValueExpression(new StringValue("test.in")));
        OpenRFile openF = new OpenRFile(new VariableExpression("a"));
        ReadRFile readF = new ReadRFile(new VariableExpression("a"), "b");
        IfStmt comparison = new IfStmt(
                new RelationalExpression(new VariableExpression("b"), new ValueExpression(new IntValue(21)), ">="),
                new PrintStmt(new VariableExpression("b")),
                new PrintStmt(new ValueExpression(new IntValue(21))));
        CompStmt program3 = new CompStmt(aDecl,
                new CompStmt(bDecl,
                        new CompStmt(aAssign,
                                new CompStmt(openF,
                                        new CompStmt(readF,
                                                new CompStmt(comparison,
                                                        new CompStmt(
                                                                new PrintStmt(new VariableExpression("a")),
                                                                new CloseRFile(new VariableExpression("a")))))))));
        return program3;
    }

    // Make a program that uses the heap statements
    public static CompStmt program4() {
        CompStmt program4 = new CompStmt(new VarDecl("v", "int"),
                new CompStmt(new VarDecl("a", "ref int"),
                        new CompStmt(new AssignStmt("v", new ValueExpression(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExpression(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new CompStmt(new WriteHeapStmt("a",
                                                new ValueExpression(new IntValue(30))),
                                                new CompStmt(new AssignStmt("v",
                                                        new ValueExpression(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(
                                                                new VariableExpression("v")),
                                                                new PrintStmt(new ReadHeapExpression(
                                                                        new VariableExpression("a"))))))),
                                                new CompStmt(new PrintStmt(
                                                        new VariableExpression("v")),
                                                        new PrintStmt(new ReadHeapExpression(
                                                                new VariableExpression("a")))))))));
        return program4;
    }

    // @Override
    // public void start(Stage stage) {
    // String javaVersion = System.getProperty("java.version");
    // String javafxVersion = System.getProperty("javafx.version");
    // Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " +
    // javaVersion + ".");
    // Scene scene = new Scene(new StackPane(l), 640, 480);
    // stage.setScene(scene);
    // stage.show();
    // }

    public static void main(String[] args) {
        CompStmt prg4 = program4();
        newProgram(prg4);
        System.out.println("\n======================================================================\n");
        // newProgram(program3());
        // System.out.println("\n======================================================================\n");
        // newProgram(program2());
        // System.out.println("\n======================================================================\n");
        // newProgram(program1());
        // System.out.println("\n======================================================================\n");
        // launch();
    }
}
