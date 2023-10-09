package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.RefValue;
import model.symbol.value.Value;

public class WriteHeapStmt implements IStmt {

    private String varName;
    private Expression expression;

    public WriteHeapStmt(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "wH(" + varName + ", " + expression.toString() + ")";
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        try {
            Value value = expression.evaluate(state.getSymTable(), state.getHeap());
            if (state.getSymTable().contains(varName)) {
                Value varValue = state.getSymTable().get(varName);
                if (varValue instanceof RefValue) {
                    RefValue refValue = (RefValue) varValue;
                    if (state.getHeap().contains(refValue.getAddress())) {
                        state.getHeap().update(refValue.getAddress(), value);
                        return state;
                    }
                    throw new StatementException("Address not found in heap");
                }
                throw new StatementException("Variable is not a reference");
            }
            throw new StatementException("Variable not found in symbol table");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        try {
            Type typeVar = typeEnv.get(varName);
            Type typeExp = expression.typeCheck(typeEnv);
            if (typeVar.equals(typeExp)) {
                return typeEnv;
            }
            throw new StatementException("WriteHeap: right hand side and left hand side have different types");
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
    }
}
