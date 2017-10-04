package mainpackage;

import javax.swing.*;
import java.awt.*;

/**
 * Affinity is based on difference between RGB Integer values,
 * the lower the affinity the better
 */
class Classroom extends JComponent {

	private Person[][] people;
	private int rows;
	private int cols;
	private int affinity;
	private JPanel panel;

	Classroom(int rows, int cols, JPanel panel) {
		this.rows = rows;
		this.cols = cols;
		this.panel = panel;
		people = new Person[rows][cols];
		generatePopulation();
		affinity = Utils.getInstance().calcAffinity(people, rows, cols);
	}

	Classroom(int rows, int cols, Person[][] people, JPanel panel) {
		this.rows = rows;
		this.cols = cols;
		this.people = people;
		this.panel = panel;
		affinity = Utils.getInstance().calcAffinity(people, rows, cols);
	}

	private void generatePopulation() {
		int id = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				people[i][j] = new Person(id, i, j);
				id++;
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				people[i][j].setNeighbors(people);
			}
		}
	}

	int getAffinity() {
		return affinity;
	}

	int getRows() {
		return rows;
	}

	int getCols() {
		return cols;
	}

	Person[][] getPeople() {
		return people;
	}

	JPanel getPanel() {
		return panel;
	}

	void draw(Graphics g, Person[][] temp) {
		super.paintComponent(g);
		int width = 50;
		int height = 50;
		int currentX = 0;
		int currentY = 0;
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[i].length; j++) {
				Person p = temp[i][j];
				int[] rgb = p.getRGB();
				g.setColor(new Color(rgb[0], rgb[1], rgb[2]));
				g.fillRect(currentX, currentY, width, height);
				currentX += width;
			}
			currentX = 0;
			currentY += height;
		}
	}

}
