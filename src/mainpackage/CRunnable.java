package mainpackage;

import javax.swing.*;
import java.util.concurrent.Exchanger;

public class CRunnable implements Runnable {

	private Classroom best;
	private final int iterations = 10;
	private Exchanger<Person[][]> exchanger;
	private Utils utils;
	private JPanel panel;

	CRunnable(Classroom classroom, Exchanger<Person[][]> exchanger, JPanel panel) {
		this.exchanger = exchanger;
		best = classroom;
		this.panel = panel;
		utils = Utils.getInstance();
	}

	@Override
	public void run() {
		int currentIteration = 0;
		Person[][] nextPeople;
		Classroom nextClass;
		while (currentIteration < iterations) {
			nextPeople = utils.rearrange(best.getPeople(), best.getRows(), best.getCols());
			nextClass = new Classroom(best.getRows(), best.getCols(), nextPeople, best.getPanel());
			if (nextClass.getAffinity() < best.getAffinity()) {
				System.out.println("Found new best for a thread");
				best = nextClass;
				best.draw(panel.getGraphics(), best.getPeople());
			}
			try {
				exchanger.exchange(best.getPeople());
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentIteration++;
		}
		utils.getBestClasses().add(best);
	}
}
