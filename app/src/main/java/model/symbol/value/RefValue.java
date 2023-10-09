package model.symbol.value;

import model.symbol.type.RefType;
import model.symbol.type.Type;

public class RefValue implements Value {

    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }

    public RefValue() {
        this.address = 0;
        this.locationType = null;
    }

    public int getAddress() {
        return address;
    }

    @Override
    public Type getType() {
        return new RefType(locationType);
    }

    @Override
    public Value defaultValue() {
        return new RefValue(0, locationType);
    }

    @Override
    public String toString() {
        return "Ref(" + address + ")";
    }

}
