package model.symbol.type;

public class RefType implements Type {

    private Type inner;

    public RefType(Type inner) {
        this.inner = inner;
    }

    public RefType() {
        this.inner = null;
    }

    public Type getInner() {
        return inner;
    }

    @Override
    public String toString() {
        return "Ref(" + inner + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RefType) {
            RefType other = (RefType) obj;
            return inner.equals(other.inner);
        }
        return false;
    }
}
