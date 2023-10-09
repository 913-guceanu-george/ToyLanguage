package model.statements;

import model.exceptions.StatementException;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;

public class Nop implements IStmt {
    @Override
    public String toString() {
        return "nop";
    }

    @Override
    public PrgState execute(PrgState state) {
        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        return typeEnv;
    }
}
