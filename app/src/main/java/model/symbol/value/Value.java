package model.symbol.value;

import model.symbol.type.Type;

public interface Value {
    Type getType();

    Value defaultValue();
}
