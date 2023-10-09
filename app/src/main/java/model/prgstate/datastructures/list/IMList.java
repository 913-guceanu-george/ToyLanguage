package model.prgstate.datastructures.list;

import model.exceptions.ADTException;

public interface IMList<Val> {
    public void add(Val Val);

    public void remove(int index) throws ADTException;

    public void remove(Val Val) throws ADTException;

    public boolean contains(Val Val);

    public Object get(int index) throws ADTException;

    public void update(int index, Val Val) throws ADTException;

    public void update(Val oldVal, Val newVal) throws ADTException;

    public int indexOf(Val Val);

    public int size();

    public IMList<Val> clone();
}
