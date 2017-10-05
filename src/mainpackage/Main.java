package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			setup();
		});
	}

	private static void setup() {
        Utils utils = Utils.getInstance();
		int nThreads = 32;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(700, 700));
		frame.setLocation(dim.width/2-frame.getSize().width/2,
				dim.height/2-frame.getSize().height/2);
		JPanel mainPanel = new JPanel();
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Classroom classroom = new Classroom(utils.getRows(), utils.getCols());
		ArrayList<Thread> threads = new ArrayList<>();
		Exchanger<Classroom> exchanger = new Exchanger<>();

		for (int i = 0; i < nThreads; i++) {
			threads.add(new Thread(new CRunnable(classroom, exchanger, mainPanel)));
		}

		for (int i = 0; i < nThreads; i++) {
			threads.get(i).start();
		}

		for (int i = 0; i < nThreads; i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		ArrayList<Classroom> bestClasses = utils.getBestClasses();
		Classroom best = bestClasses.get(0);
		for (Classroom bestClass : bestClasses) {
			if (best != bestClass && bestClass.getAffinity() < best.getAffinity()) {
				best = bestClass;
			}
		}
		System.out.println("Done with sorting");
        mainPanel.removeAll();
		FinalPanel finalPanel = new FinalPanel(utils.getRows(), utils.getCols(), best.getPeople(), mainPanel);
		mainPanel.add(finalPanel);
		finalPanel.revalidate();
	}

}
