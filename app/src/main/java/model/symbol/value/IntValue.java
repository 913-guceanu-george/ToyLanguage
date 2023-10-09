package model.symbol.value;

import model.symbol.type.IntType;
import model.symbol.type.Type;

public class IntValue implements Value {
    private Integer value;

    public IntValue() {
        this.value = null;
    }

    public IntValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IntValue) {
            IntValue other = (IntValue) obj;
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public Type getType() {
        return new IntType();
    }

    @Override
    public String toString() {
        if (value == null)
            return "notDefined";
        return Integer.toString(value);
    }

    @Override
    public Value defaultValue() {
        return new IntValue();
    }
}
