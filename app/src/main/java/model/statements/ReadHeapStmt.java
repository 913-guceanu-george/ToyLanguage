package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.RefValue;
import model.symbol.value.Value;

public class ReadHeapStmt implements IStmt {

    private Expression expression;

    public ReadHeapStmt(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            Value value = expression.evaluate(state.getSymTable(), state.getHeap());
            if (value.equals(new RefValue()))
                throw new StatementException("Address not found in heap!");
            if (state.getSymTable().contains(value.toString())) {
                state.getSymTable().update(value.toString(), value);
                return state;
            }
            state.getSymTable().add(value.toString(), value);
            return state;
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeVar = typeEnv.get(expression.toString());
            Type typeExp = expression.typeCheck(typeEnv);
            if (typeVar.equals(typeExp)) {
                return typeEnv;
            }
            throw new StatementException("ReadHeap: right hand side and left hand side have different types");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }
}
