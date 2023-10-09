package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class VariableExpression implements Expression {
    private String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        if (table.contains(id)) {
            return table.get(id);
        }
        if (heap.contains(Integer.parseInt(id))) {
            return heap.get(Integer.parseInt(id));
        }
        throw new ExpressionException("Variable " + id + " not found in the symbol table or heap table.");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        if (typeEnv.contains(id)) {
            return typeEnv.get(id);
        }
        throw new ExpressionException("Variable " + id + " not found in the symbol table.");
    }
}
