package model.statements;

import model.exceptions.StatementException;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;

public class ForkStmt implements IStmt {
    private IStmt statement;

    public ForkStmt(IStmt statement) {
        this.statement = statement;
    }

    @Override
    public PrgState execute(PrgState state) {
        return new PrgState(state.getSymTable().clone(), state.getExeStack().clone(), state.getOut().clone(),
                state.getHeap().clone(), this.statement);
    }

    @Override
    public String toString() {
        return "fork(" + this.statement.toString() + ")";
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        return this.statement.typeCheck(typeEnv);
    }
}
