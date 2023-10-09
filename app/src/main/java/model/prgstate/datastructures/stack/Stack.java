package model.prgstate.datastructures.stack;

import java.util.ArrayList;
import java.util.List;

public class Stack<Val> implements IStack<Val> {

    private List<Val> stack;

    public Stack() {
        stack = new ArrayList<Val>();
    }

    @Override
    public void push(Val Val) {
        this.stack.add(Val);
    }

    @Override
    public Val pop() {
        return this.stack.remove(0);
    }

    @Override
    public Val popLast() {
        return this.stack.remove(this.stack.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.size() == 0;
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "";
        String result = "";
        for (Val Val : this.stack) {
            if (Val == null)
                result += "null ";
            else
                result += Val.toString() + " ";
        }
        return result;
    }

    @Override
    public Stack<Val> clone() {
        Stack<Val> newStack = new Stack<Val>();
        for (Val Val : this.stack) {
            newStack.push(Val);
        }
        return newStack;
    }
}