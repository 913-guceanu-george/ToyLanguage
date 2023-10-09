package repository;

import java.util.List;

import model.exceptions.RepositoryException;
import model.prgstate.PrgState;

public interface IRepository {
    void addPrgState(PrgState state);

    void logPrgStateExec(PrgState state) throws RepositoryException;

    public List<PrgState> getPrgList();

    void setPrgList(List<PrgState> prgList);
}
