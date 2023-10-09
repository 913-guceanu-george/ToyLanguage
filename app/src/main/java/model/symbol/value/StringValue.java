package model.symbol.value;

import model.symbol.type.StringType;
import model.symbol.type.Type;

public class StringValue implements Value {
    private String value;

    public StringValue() {
        this.value = null;
    }

    public StringValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public boolean equals(Object obj) {
        if (obj instanceof StringValue) {
            StringValue other = (StringValue) obj;
            return this.value.equals(other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return this.value;
    }

    @Override
    public Type getType() {
        return new StringType();
    }

    @Override
    public Value defaultValue() {
        return new StringValue("");
    }
}
