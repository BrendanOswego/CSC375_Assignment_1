package mainpackage;

public class Population {

	Person[][] people;
	private int rows;
	private int cols;

	Population(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		people = new Person[rows][cols];
	}

	public void generatePopulation () {
		int index = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				people[i][j] = new Person(index, i, j);
				index++;
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				people[i][j].setNeighbors(people);
				System.out.println(people[i][j]);
			}
		}
	}
}
