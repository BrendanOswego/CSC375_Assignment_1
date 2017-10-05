package mainpackage;

import javax.swing.*;
import java.util.concurrent.Exchanger;

public class CRunnable implements Runnable {

	private Classroom best;
	private final int iterations = 100;
	private Utils utils;
	private JPanel panel;
	private volatile boolean shutdown = false;
	private Exchanger<Classroom> exchanger;

	CRunnable(Classroom classroom, Exchanger<Classroom> exchanger, JPanel panel) {
		best = classroom;
		this.panel = panel;
		this.exchanger = exchanger;
		utils = Utils.getInstance();
	}

	@Override
	public void run() {
        int currentIteration = 0;
        Person[][] nextPeople;
        Classroom nextClass;
        while (currentIteration < iterations && !shutdown) {
            if (utils.findDifferent(best.getPeople()) == 0) {
                shutdown();
            }
            nextPeople = utils.rearrange(best.getPeople(), utils.getRows(), utils.getCols());
            nextClass = new Classroom(best.getRows(), best.getCols(), nextPeople);
            while (utils.findDifferent(nextClass.getPeople()) > utils.findDifferent(best.getPeople())) {
                nextPeople = utils.rearrange(best.getPeople(), best.getRows(), best.getCols());
                nextClass = new Classroom(best.getRows(), best.getCols(), nextPeople);
            }
            try {
                best = exchanger.exchange(nextClass);
                best.draw(panel.getGraphics(), best.getPeople());
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            currentIteration++;
        }
        utils.getBestClasses().add(best);
	}

	private void shutdown() {
	    shutdown = true;
    }


}
