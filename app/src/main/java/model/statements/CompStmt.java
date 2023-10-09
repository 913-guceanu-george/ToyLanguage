package model.statements;

import model.exceptions.StatementException;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;

public class CompStmt implements IStmt {
    private IStmt first;
    private IStmt second;

    public CompStmt(IStmt first, IStmt second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return first.toString() + "; " + second.toString() + " ";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        state.getExeStack().push(first);
        state.getExeStack().push(second);
        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        return second.typeCheck(first.typeCheck(typeEnv));
    }
}
