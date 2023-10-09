package model.prgstate.datastructures.list;

import model.exceptions.ADTException;

public class MList<Val> implements IMList<Val> {
    private Object[] list;
    private int size;

    public MList() {
        this.list = new Object[10];
        this.size = 0;
    }

    @Override
    public void add(Val Val) {
        if (this.size == this.list.length) {
            this.resize();
        }

        this.list[this.size] = Val;
        this.size++;
    }

    @Override
    public void remove(int index) throws ADTException {
        if (index < 0 || index >= this.size) {
            throw new ADTException("Index out of bounds");
        }

        for (int i = index; i < this.size - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.size--;
    }

    @Override
    public void remove(Val Val) throws ADTException {
        int index = this.indexOf(Val);

        if (index == -1) {
            throw new ADTException("Val not found");
        }

        this.remove(index);
    }

    @Override
    public boolean contains(Val Val) {
        return this.indexOf(Val) != -1;
    }

    @Override
    public Object get(int index) throws ADTException {
        if (index < 0 || index >= this.size) {
            throw new ADTException("Index out of bounds");
        }

        return this.list[index];
    }

    @Override
    public void update(int index, Val Val) throws ADTException {
        if (index < 0 || index >= this.size) {
            throw new ADTException("Index out of bounds");
        }

        this.list[index] = Val;
    }

    @Override
    public void update(Val oldVal, Val newVal) throws ADTException {
        int index = this.indexOf(oldVal);

        if (index == -1) {
            throw new ADTException("Val not found");
        }

        this.update(index, newVal);
    }

    @Override
    public int indexOf(Val Val) {
        for (int i = 0; i < this.size; i++) {
            if (this.list[i].equals(Val)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int size() {
        return this.size;
    }

    // toString method
    @Override
    public String toString() {
        if (this.size == 0)
            return "";
        String result = "";

        for (int i = 0; i < this.size; i++) {
            result += this.list[i].toString() + "| ";
        }

        return result;
    }

    private void resize() {
        Object[] newList = new Object[this.list.length + 10];

        for (int i = 0; i < this.list.length; i++) {
            newList[i] = this.list[i];
        }

        this.list = newList;
    }

    @Override
    public IMList<Val> clone() {
        IMList<Val> newList = new MList<Val>();

        for (int i = 0; i < this.size; i++) {
            newList.add((Val) this.list[i]);
        }

        return newList;
    }
}
