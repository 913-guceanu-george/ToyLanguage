package model.expressions;

import model.exceptions.ExpressionException;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.BoolType;
import model.symbol.type.IntType;
import model.symbol.type.Type;
import model.symbol.value.BoolValue;
import model.symbol.value.IntValue;
import model.symbol.value.Value;

public class RelationalExpression implements Expression {

    private Expression left;
    private Expression right;
    private String operator;

    public RelationalExpression(Expression left, Expression right, String operator) {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }

    @Override
    public String toString() {
        return left.toString() + operator + right.toString();
    }

    @Override
    public Value evaluate(IDict<String, Value> table, IDict<Integer, Value> heap) throws ExpressionException {
        Value leftValue = left.evaluate(table, heap);
        Value rightValue = right.evaluate(table, heap);
        if (leftValue.getType().equals(new IntType())) {
            if (rightValue.getType().equals(new IntType())) {
                IntValue leftIntValue = (IntValue) leftValue;
                IntValue rightIntValue = (IntValue) rightValue;
                int leftInt = leftIntValue.getValue();
                int rightInt = rightIntValue.getValue();
                if (operator.equals("<")) {
                    return new BoolValue(leftInt < rightInt);
                }
                if (operator.equals("<=")) {
                    return new BoolValue(leftInt <= rightInt);
                }
                if (operator.equals("==")) {
                    return new BoolValue(leftInt == rightInt);
                }
                if (operator.equals("!=")) {
                    return new BoolValue(leftInt != rightInt);
                }
                if (operator.equals(">")) {
                    return new BoolValue(leftInt > rightInt);
                }
                if (operator.equals(">=")) {
                    return new BoolValue(leftInt >= rightInt);
                }
            } else {
                throw new ExpressionException("Right operand is not an integer");
            }
        } else {
            throw new ExpressionException("Left operand is not an integer");
        }
        throw new ExpressionException("Invalid operator");
    }

    @Override
    public Type typeCheck(IDict<String, Type> typeEnv) throws ExpressionException {
        Type leftType = left.typeCheck(typeEnv);
        Type rightType = right.typeCheck(typeEnv);
        if (leftType.equals(new IntType())) {
            if (rightType.equals(new IntType())) {
                return new BoolType();
            } else {
                throw new ExpressionException("Right operand is not an integer");
            }
        } else {
            throw new ExpressionException("Left operand is not an integer");
        }
    }
}
