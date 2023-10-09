package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.BoolType;
import model.symbol.type.Type;
import model.symbol.value.BoolValue;

public class WhileStatement implements IStmt {
    private Expression condition;
    private IStmt body;

    public WhileStatement(Expression condition, IStmt body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while (" + condition + ") { " + body + " }";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            if (condition.evaluate(state.getSymTable(), state.getHeap()).equals(new BoolValue(true))) {
                state.getExeStack().push(this);
                state.getExeStack().push(body);
            }
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        return null;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type condType = condition.typeCheck(typeEnv);
            if (condType.equals(new BoolType())) {
                body.typeCheck(typeEnv);
                return typeEnv;
            }
            throw new StatementException("Condition is not a boolean!");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }
}
