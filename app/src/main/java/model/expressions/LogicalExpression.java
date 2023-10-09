package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.BoolType;
import model.symbol.type.Type;
import model.symbol.value.BoolValue;
import model.symbol.value.Value;

public class LogicalExpression implements Expression {
    private Expression left;
    private Expression right;
    private char operator;

    public LogicalExpression(Expression left, Expression right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = left.evaluate(table, heap);
        v2 = right.evaluate(table, heap);
        if (v1.getType().equals(new BoolType())) {
            if (v2.getType().equals(new BoolType())) {
                boolean b1, b2;
                b1 = ((BoolValue) v1).getValue();
                b2 = ((BoolValue) v2).getValue();
                switch (this.operator) {
                    case '&':
                        return new BoolValue(b1 && b2);
                    case '|':
                        return new BoolValue(b2 || b1);
                    default:
                        return v1.getType().equals(v2.getType()) ? new BoolValue(false) : new BoolValue(true);
                }
            }
        }
        throw new ExpressionException("Operands are not of the same type");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        Type type1, type2;
        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);
        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            throw new ExpressionException("Second operand is not a boolean");
        }
        throw new ExpressionException("First operand is not a boolean");
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString();
    }
}
