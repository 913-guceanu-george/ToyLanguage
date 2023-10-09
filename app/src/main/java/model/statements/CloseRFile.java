package model.statements;

import java.io.IOException;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.StringValue;

public class CloseRFile implements IStmt {
    private Expression exp;

    public CloseRFile(Expression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        StringValue fd;
        try {
            fd = (StringValue) exp.evaluate(state.getSymTable(), state.getHeap());
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        if (state.getFileTable().contains(fd)) {
            try {
                state.getFileTable().get(fd).close();
            } catch (IOException e) {
                throw new StatementException(e.getMessage());
            }
            state.getFileTable().remove(fd);
            return state;
        }
        throw new StatementException("File descriptor not found!");

    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeExp = exp.typeCheck(typeEnv);
            if (typeExp.equals(new model.symbol.type.StringType())) {
                return typeEnv;
            }
            throw new StatementException("CloseRFile: expression is not a string");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "closeRFile(" + exp.toString() + ")";
    }
}
