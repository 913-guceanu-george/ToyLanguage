package model.prgstate.datastructures.stack;

public interface IStack<Value> {
    public void push(Value value);

    public Value pop();

    public Value popLast();

    public boolean isEmpty();

    public String toString();

    public IStack<Value> clone();
}
