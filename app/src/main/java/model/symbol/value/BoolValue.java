package model.symbol.value;

import model.symbol.type.BoolType;
import model.symbol.type.Type;

public class BoolValue implements Value {
    private Boolean value;

    public BoolValue() {
        this.value = null;
    }

    public BoolValue(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BoolValue) {
            BoolValue other = (BoolValue) obj;
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public Type getType() {
        return new BoolType();
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public Value defaultValue() {
        return new BoolValue();
    }
}
