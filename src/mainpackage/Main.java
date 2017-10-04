package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Exchanger;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			setup();
		});
	}

	private static void setup() {
		int nThreads = 64;
		int rows = 16;
		int cols = 16;
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		GridLayout layout = new GridLayout(rows, cols);
		layout.setHgap(5);
		layout.setVgap(5);
		mainPanel.setLayout(layout);
		frame.setSize(new Dimension(1280, 720));
		frame.setContentPane(mainPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Classroom classroom = new Classroom(rows, cols, mainPanel);
		ArrayList<Thread> threads = new ArrayList<>();
		Exchanger<Person[][]> exchanger = new Exchanger<>();

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

		Utils utils = Utils.getInstance();
		ArrayList<Classroom> bestClasses = utils.getBestClasses();
		Classroom best = bestClasses.get(0);
		for (Classroom bestClass : bestClasses) {
			if (best != bestClass && bestClass.getAffinity() < best.getAffinity()) {
				best = bestClass;
			}
		}
		System.out.println("Done with sorting");
		classroom.draw(mainPanel.getGraphics(), best.getPeople());
	}

}
