package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.IntType;
import model.symbol.type.Type;
import model.symbol.value.IntValue;
import model.symbol.value.Value;

public class ArithemticExpression implements Expression {
    private Expression left;
    private Expression right;
    private char operator;

    public ArithemticExpression(Expression left, Expression right, char operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        Value v1, v2;
        v1 = left.evaluate(table, heap);
        v2 = right.evaluate(table, heap);
        if (v1.getType().equals(new IntType())) {
            if (v2.getType().equals(new IntType())) {
                int n1 = ((IntValue) v1).getValue();
                int n2 = ((IntValue) v2).getValue();
                switch (this.operator) {
                    case '+':
                        return new IntValue(n1 + n2);
                    case '-':
                        return new IntValue(n1 - n2);
                    case '*':
                        return new IntValue(n1 * n2);
                    case '/':
                        if (n2 == 0) {
                            throw new ExpressionException("Division by zero!");
                        }
                        return new IntValue(n1 / n2);
                    default:
                        return new IntValue(0);
                }
            }
            throw new ExpressionException("Second operand is not an integer");
        }
        throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        Type type1, type2;
        type1 = left.typeCheck(typeEnv);
        type2 = right.typeCheck(typeEnv);
        if (type1.equals(new IntType())) {
            if (type2.equals(new IntType())) {
                return new IntType();
            }
            throw new ExpressionException("Second operand is not an integer");
        }
        throw new ExpressionException("First operand is not an integer");
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString();
    }
}
