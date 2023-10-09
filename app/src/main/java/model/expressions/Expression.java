package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.Type;
import model.symbol.value.Value;

public interface Expression {
    Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException;

    Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException;
}
