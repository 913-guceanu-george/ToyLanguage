package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.*;
import model.symbol.value.*;

public class IfStmt implements IStmt {
    private Expression condition;
    private IStmt thenStmt;
    private IStmt elseStmt;

    public IfStmt(Expression condition, IStmt thenStmt, IStmt elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public String toString() {
        return "if (" + condition + ") then (" + thenStmt + ") else (" + elseStmt + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {

            Value condValue = condition.evaluate(state.getSymTable(), state.getHeap());
            int toCheck = Integer.MIN_VALUE;
            boolean toCheckBool = false;
            if (condValue.getType().equals(new BoolType())) {
                toCheckBool = ((BoolValue) condValue).getValue();
                if (toCheckBool) {
                    state.getExeStack().push(this.thenStmt);
                    return state;
                }
                state.getExeStack().push(this.elseStmt);
                return state;
            }
            if (condValue.getType().equals(new IntType())) {
                toCheck = ((IntValue) condValue).getValue();
                if (toCheck != 0) {
                    state.getExeStack().push(this.thenStmt);
                    return state;
                }
                state.getExeStack().push(this.elseStmt);
                return state;
            }
            throw new StatementException("Invalid condition type!");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }

    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        Type typeExp;
        try {
            typeExp = condition.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        if (typeExp.equals(new BoolType())) {
            thenStmt.typeCheck(typeEnv.clone());
            elseStmt.typeCheck(typeEnv.clone());
            return typeEnv;
        } else {
            throw new StatementException("The condition of IF has not the type bool");
        }
    }
}
