package view;

import controller.Controller;
import model.prgstate.PrgState;

public class View {
    private Controller ctrl;

    public View(Controller ctrl) {
        this.ctrl = ctrl;
    }

    public void addPrgState(PrgState state) {
        this.ctrl.addPrgState(state);
    }

    public void run() {
        try {
            this.ctrl.allSteps();
            String so = this.ctrl.toString();
            System.out.println(so);
        } catch (Exception e) {
            String so = this.ctrl.toString();
            System.out.println(so);
            System.out.println(e.getMessage());
        }
    }
}
