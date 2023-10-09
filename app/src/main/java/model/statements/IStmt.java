package model.statements;

import model.exceptions.StatementException;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;

public interface IStmt {
    public String toString();

    public PrgState execute(PrgState state) throws StatementException;

    IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException;
}
