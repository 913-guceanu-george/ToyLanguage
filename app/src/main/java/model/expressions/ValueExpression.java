package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class ValueExpression implements Expression {
    private Value value;

    public ValueExpression(Value value) {
        this.value = value;
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        return value.getType();
    }

}
