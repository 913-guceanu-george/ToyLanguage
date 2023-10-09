package model.statements;

import java.io.BufferedReader;
import java.io.IOException;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.IntType;
import model.symbol.type.Type;
import model.symbol.value.IntValue;
import model.symbol.value.StringValue;

public class ReadRFile implements IStmt {
    private Expression exp;
    private String varName;
    private StringValue fileName;

    public ReadRFile(Expression exp, String varName) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        if (!state.getSymTable().contains(varName)) {
            throw new StatementException("Variable " + varName + " is not defined!");
        }
        if (!state.getSymTable().get(varName).getType().equals(new IntType())) {
            throw new StatementException("Variable " + varName + " is not an integer!");
        }
        try {
            fileName = (StringValue) exp.evaluate(state.getSymTable(), state.getHeap());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        if (!state.getFileTable().contains(fileName)) {
            throw new StatementException("File " + fileName + " is not open!");
        }
        BufferedReader reader = state.getFileTable().get(fileName);
        try {
            String line = reader.readLine();
            if (line == null) {
                throw new StatementException("File " + fileName + " is empty!");
            }
            state.getSymTable().add(varName, new IntValue(Integer.parseInt(line)));

        } catch (IOException e) {
            throw new StatementException("Error reading from file " + fileName + "!");
        }
        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeExp = exp.typeCheck(typeEnv);
            if (typeExp.equals(new model.symbol.type.StringType())) {
                if (typeEnv.contains(varName)) {
                    if (typeEnv.get(varName).equals(new IntType())) {
                        return typeEnv;
                    }
                    throw new StatementException("ReadRFile: variable " + varName + " is not an integer");
                }
                throw new StatementException("ReadRFile: variable " + varName + " is not defined");
            }
            throw new StatementException("ReadRFile: expression is not a string");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        if (fileName == null) {
            return "read(" + this.exp.toString() + ", " + varName + ")";
        }
        return "read(" + this.fileName.getValue() + ", " + varName + ")";
    }
}
