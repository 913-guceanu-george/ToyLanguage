package controller;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import model.exceptions.ExecException;
import model.exceptions.RepositoryException;
import model.prgstate.PrgState;
import repository.IRepository;

public class Controller {
    private IRepository repo;
    private String fin = "";
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public void addPrgState(PrgState state) {
        this.repo.addPrgState(state);
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        // Make the result modifiable
        return inPrgList.stream()
                .filter(p -> p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws ExecException {
        // before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
        List<PrgState> newPrgList = prgList.stream()
                .map(prg -> {
                    try {
                        return prg.oneStep();
                    } catch (ExecException e) {
                        e.printStackTrace();
                    }
                    return null;
                })
                .toList();
        prgList.addAll(newPrgList);
        // after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (RepositoryException e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }

    public void allSteps() throws ExecException {
        // create the executor
        this.executor = Executors.newFixedThreadPool(2);
        // remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        // while there are any incompleted programs
        while (prgList.size() > 0) {
            // call the safeGarbageCollector
            prgList.forEach(prg -> {
                prg.getHeap().setContent(prg.safeGarbageCollector(
                        prg.getAddrFromSymTable(prg.getSymTable().values()), prg.getHeap().getContent()));

            });
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    // toString method
    @Override
    public String toString() {
        return this.fin;
    }
}
