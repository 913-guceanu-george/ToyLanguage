package repository;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import model.exceptions.RepositoryException;
import model.prgstate.PrgState;

public class Repository implements IRepository {
    private List<PrgState> repo;
    private String logFilePath;

    public Repository() {
        this.repo = new ArrayList<PrgState>();
        this.logFilePath = "interpreter_log.txt";
    }

    @Override
    public void logPrgStateExec(PrgState state) throws RepositoryException {
        try {

            PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
            file.println(state.toString());
            file.close();
        } catch (IOException e) {
            throw new RepositoryException(logFilePath + " could not be opened for writing");
        }
    }

    @Override
    public void addPrgState(PrgState state) {
        this.repo.add(state);
    }

    public List<PrgState> getPrgList() {
        return this.repo;
    }

    public void setPrgList(List<PrgState> prgList) {
        this.repo = prgList;
    }

    // toString method
    @Override
    public String toString() {
        String result = "";
        for (PrgState state : this.repo) {
            result += state.toString();
            result += "========================================\n";
        }
        return result;
    }
}