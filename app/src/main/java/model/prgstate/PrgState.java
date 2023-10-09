package model.prgstate;

import java.io.BufferedReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.exceptions.ExecException;
import model.exceptions.StatementException;
import model.prgstate.datastructures.dictionary.Dict;
import model.prgstate.datastructures.dictionary.IDict;
import model.prgstate.datastructures.list.IMList;
import model.prgstate.datastructures.stack.IStack;
import model.statements.IStmt;
import model.symbol.value.RefValue;
import model.symbol.value.StringValue;
import model.symbol.value.Value;

public class PrgState {
    private IDict<String, Value> symTable;
    private IDict<StringValue, BufferedReader> fileTable;
    private IStack<IStmt> exeStack;
    private IMList<Value> out;
    private IStmt originalProgram;
    private IDict<Integer, Value> heap;
    private int id = 0;
    private static int nextId = 0;

    public PrgState(IDict<String, Value> symTable, IStack<IStmt> exeStack, IMList<Value> out,
            IDict<Integer, Value> heap, IStmt originalProgram) {
        this.symTable = symTable;
        this.exeStack = exeStack;
        this.out = out;
        this.heap = heap;
        this.originalProgram = originalProgram;
        this.fileTable = new Dict<>();
        this.exeStack.push(this.originalProgram);
    }

    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()) || isReferenced(e.getKey(), heap))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private boolean isReferenced(Integer key, Map<Integer, Value> heap2) {
        return heap2.values().stream()
                .anyMatch(v -> v instanceof RefValue && ((RefValue) v).getAddress() == key);
    }

    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> {
                    RefValue v1 = (RefValue) v;
                    return v1.getAddress();
                }).toList();
    }

    public PrgState oneStep() throws ExecException {
        if (this.exeStack.isEmpty()) {
            throw new ExecException("Execution stack is empty");
        }
        IStmt currentStmt = this.exeStack.pop();
        try {
            return currentStmt.execute(this);
        } catch (StatementException e) {
            throw new ExecException(e.getMessage());
        }
    }

    public synchronized static int getNextId() {
        return nextId++;
    }

    public boolean isNotCompleted() {
        return !this.exeStack.isEmpty();
    }

    public int getFreeAddress() {
        this.id++;
        return this.id;
    }

    public IDict<Integer, Value> getHeap() {
        return heap;
    }

    public IDict<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IDict<String, Value> getSymTable() {
        return symTable;
    }

    public IStack<IStmt> getExeStack() {
        return exeStack;
    }

    public IMList<Value> getOut() {
        return out;
    }

    public IStmt getOriginalProgram() {
        return originalProgram;
    }

    @Override
    public String toString() {
        return "ID= " + this.id + "\n" +
                "PrgState::= " + "\n" +
                "symTable= " + symTable.toString() + "\n" +
                "heap= " + heap.toString() + "\n" +
                "exeStack= " + exeStack.toString() + "\n" +
                "out= " + out.toString() + "\n" +
                "fileTable= " + fileTable.toString() + "\n" +
                "originalProgram= " + originalProgram + "\n" +
                "\n--------------------------------------------\n";
    }

}
