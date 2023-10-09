package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Expression expression;

    public AssignStmt(String id, Expression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            Value value = expression.evaluate(state.getSymTable(), state.getHeap());
            if (state.getSymTable().contains(id)) {
                state.getSymTable().update(id, value);
                return state;
            }
            state.getSymTable().add(id, value);
            return state;
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeVar = typeEnv.get(id);
            Type typeExp = expression.typeCheck(typeEnv);
            if (typeVar.equals(typeExp)) {
                return typeEnv;
            }
            throw new StatementException("AssignStmt: right hand side and left hand side have different types");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }
}