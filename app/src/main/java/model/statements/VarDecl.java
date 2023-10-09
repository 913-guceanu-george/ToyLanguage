package model.statements;

import model.exceptions.StatementException;
import model.prgstate.PrgState;
import model.prgstate.datastructures.dictionary.IDict;
import model.symbol.type.BoolType;
import model.symbol.type.IntType;
import model.symbol.type.RefType;
import model.symbol.type.StringType;
import model.symbol.type.Type;
import model.symbol.value.BoolValue;
import model.symbol.value.IntValue;
import model.symbol.value.RefValue;
import model.symbol.value.StringValue;

public class VarDecl implements IStmt {
    private String id;
    private String type;

    public VarDecl(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + id;
    }

    @Override
    public PrgState execute(PrgState state) throws StatementException {
        if (state.getSymTable().contains(id)) {
            throw new StatementException("Variable " + id + " already declared!");
        }
        if (type.startsWith("int")) {
            state.getSymTable().add(id, new IntValue());
            return state;
        }
        if (type.startsWith("bool")) {
            state.getSymTable().add(id, new BoolValue());
            return state;
        }
        if (type.startsWith("string")) {
            state.getSymTable().add(id, new StringValue());
            return state;
        }
        if (type.startsWith("ref")) {
            state.getSymTable().add(id, new RefValue());
            return state;
        }
        throw new StatementException("Invalid type " + type + "!");
    }

    @Override
    public IDict<String, Type> typeCheck(IDict<String, Type> typeEnv) throws StatementException {
        if (typeEnv.contains(id)) {
            throw new StatementException("Variable " + id + " already declared!");
        }
        if (type.startsWith("int")) {
            typeEnv.add(id, new IntType());
            return typeEnv;
        }
        if (type.startsWith("bool")) {
            typeEnv.add(id, new BoolType());
            return typeEnv;
        }
        if (type.startsWith("string")) {
            typeEnv.add(id, new StringType());
            return typeEnv;
        }
        if (type.startsWith("ref")) {
            typeEnv.add(id, new RefType());
            return typeEnv;
        }
        throw new StatementException("Invalid type " + type + "!");
    }
}
