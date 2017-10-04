package mainpackage;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Utils {

	private static Utils utils = null;
	private ArrayList<Classroom> bestClasses = new ArrayList<>();

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

	public ArrayList<Classroom> getBestClasses() {
		return bestClasses;
	}

}
