package model.statements;

import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class PrintStmt implements IStmt {
    private Expression exp;

    public PrintStmt(Expression exp) {
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        Value val;
        try {
            val = this.exp.evaluate(state.getSymTable(), state.getHeap());
        } catch (Exception e) {
            throw new StatementException(e.getMessage());
        }

        state.getOut().add(val);

        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            this.exp.typeCheck(typeEnv);
        } catch (Exception e) {
            throw new StatementException(e.getMessage());
        }
        return typeEnv;
    }

    @Override
    public String toString() {
        return "print(" + this.exp.toString() + ")";
    }
}