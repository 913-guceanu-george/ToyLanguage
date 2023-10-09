package model.statements;

import model.exceptions.ExpressionException;
import model.exceptions.StatementException;
import model.expressions.Expression;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.RefType;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class NewStmt implements IStmt {

    private String varName;
    private Expression exp;

    public NewStmt(String varName, Expression exp) {
        this.varName = varName;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        IDict<String, Value> symTable = state.getSymTable();
        IDict<Integer, Value> heap = state.getHeap();
        Value value;
        if (symTable.contains(varName)) {
            value = symTable.get(varName);
        } else {
            throw new StatementException("New: variable is not defined");
        }
        if (value.getType().equals(new RefType())) {
            int address = state.getFreeAddress();
            try {
                heap.add(address, exp.evaluate(symTable, heap));
            } catch (ExpressionException e) {
                throw new StatementException(e.getMessage());
            }
        } else {
            throw new StatementException("New: variable is not a reference type");
        }
        return state;
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        Type typeVar = typeEnv.get(varName);
        Type typeExp;
        try {
            typeExp = exp.typeCheck(typeEnv);
        } catch (ExpressionException e) {
            throw new StatementException(e.getMessage());
        }
        if (typeVar.equals(new RefType(typeExp))) {
            return typeEnv;
        } else {
            throw new StatementException("New: right hand side and left hand side have different types");
        }
    }

    @Override
    public String toString() {
        return "new(" + varName + ", " + exp.toString() + ")";
    }
}