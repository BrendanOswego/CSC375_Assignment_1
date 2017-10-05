package mainpackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

class Utils {

	private static Utils utils = null;
	private ArrayList<Classroom> bestClasses = new ArrayList<>();
	private int rows = 8;
	private int cols = 4;
	private int width = 50;
	private int height = 50;

	static Utils getInstance() {
		if (utils == null) {
			utils = new Utils();
		}
		return utils;
	}

	int calcAffinity(Person[][] people, int rows, int cols) {
		int affinity = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				affinity += people[i][j].calcAffinity();
			}
		}
		return affinity;
	}

	Person[][] rearrange(Person[][] people, int rows, int cols) {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (int i = 0; i < people.length; i++) {
			for (int j = 0; j < people[i].length; j++) {
				int newRow = rand.nextInt(people.length);
				int newCol = rand.nextInt(people[i].length);
				Person tempPerson = people[i][j];
				people[i][j] = people[newRow][newCol];
				people[newRow][newCol] = tempPerson;
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				people[i][j].setNeighbors(people);
			}
		}
		return people;
	}

	int findDifferent(Person[][] people) {
		int count = 0;
		Person temp = people[0][0];
		for (int i = 0; i < people.length; i++) {
			for (int j = 0; j < people[i].length; j++) {
				if (people[i][j] != temp && people[i][j].getRGB() != temp.getRGB()) {
					count++;
				}
			}
		}
		return count;
	}

	ArrayList<Classroom> getBestClasses() {
		return bestClasses;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getWidth() {
	    return width;
    }

    public int getHeight () {
	    return height;
    }

}
