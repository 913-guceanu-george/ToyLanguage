package model.statements;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.StringValue;

public class OpenRFile implements IStmt {

    private Expression exp;

    public OpenRFile(Expression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            StringValue val = (StringValue) exp.evaluate(state.getSymTable(), state.getHeap());
            if (state.getFileTable().contains(val)) {
                throw new StatementException("File already opened");
            }
            BufferedReader reader = new BufferedReader(new FileReader(val.getValue()));
            state.getFileTable().add(val, reader);

        } catch (ExpressionException | IOException e) {
            throw new StatementException(e.getMessage());
        }
        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeExp = exp.typeCheck(typeEnv);
            if (typeExp.equals(new model.symbol.type.StringType())) {
                return typeEnv;
            }
            throw new StatementException("OpenRFile: expression is not a string");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "openRFile(" + exp.toString() + ")";
    }
}
