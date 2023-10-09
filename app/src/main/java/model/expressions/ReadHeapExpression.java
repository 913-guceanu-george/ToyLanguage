package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.RefType;
import model.symbol.type.Type;
import model.symbol.value.Value;

public class ReadHeapExpression implements Expression {
    private Expression exp;

    public ReadHeapExpression(Expression exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return exp.toString();
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        return exp.evaluate(table, heap);
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        Type type = exp.typeCheck(typeEnv);
        if (type instanceof RefType) {
            return ((RefType) type).getInner();
        } else {
            throw new ExpressionException("The type of the expression is not a reference type.");
        }
    }
}
