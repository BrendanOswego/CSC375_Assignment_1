package mainpackage;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Person {

	private Person[] neighbors = new Person[4];
	private int id;
	private int row;
	private int col;
	private int[] rgb = new int[3];
	private int affinity;

	private Person() {
		this.id = -1;
		this.row = -1;
		this.col = -1;
	}

	Person(int id, int row, int col) {
		this.id = id;
		this.row = row;
		this.col = col;
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (int i = 0; i < 3; i++) {
			rgb[i] = rand.nextInt(63);
		}
	}

	public void setNeighbors(Person[][] people) {
		//check top right bottom left
		neighbors[0] = (row - 1 >= 0) ? people[row - 1][col] : new Person();
		neighbors[1] = (col + 1 < people[row].length) ? people[row][col + 1] : new Person();
		neighbors[2] = (row + 1 < people.length) ? people[row + 1][col] : new Person();
		neighbors[3] = (col - 1 >= 0) ? people[row][col - 1] : new Person();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public Person[] getNeighbors() {
		return neighbors;
	}

	@Override
	public String toString() {
		String[] neighborIDs = new String[3];
		for (int i = 0; i < 3; i++) {
			neighborIDs[i] = String.valueOf(neighbors[i].getId());
		}
		return "Person: " + id + '\n' + "Row: " + row + '\n' +
						"Col: " + col + '\n' + "Neighbors: " + Arrays.toString(neighborIDs);
	}
}
