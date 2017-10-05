package mainpackage;

import java.util.concurrent.ThreadLocalRandom;

class Person {

	private Person[] neighbors = new Person[4];
	private int id;
	private int row;
	private int col;
	private int[] rgb = new int[3];

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

	void setNeighbors(Person[][] people) {
		//check top right bottom left
		neighbors[0] = (row - 1 >= 0) ? people[row - 1][col] : new Person();
		neighbors[1] = (col + 1 < people[row].length) ? people[row][col + 1] : new Person();
		neighbors[2] = (row + 1 < people.length) ? people[row + 1][col] : new Person();
		neighbors[3] = (col - 1 >= 0) ? people[row][col - 1] : new Person();
	}

	int calcAffinity() {
		int temp = 0;
		int top = getDiff(0);
		int right = getDiff(1);
		int bottom = getDiff(2);
		int left = getDiff(3);
		temp += top + right + bottom + left;
		return temp;
	}

	private int getDiff(int cardinal) {
		int diff = 63 * 3;
		Person neighbor = neighbors[cardinal];
		if (neighbor != null && neighbor.getId() != -1) {
			int temp = 0;
			temp += Math.abs(rgb[0] - neighbor.rgb[0]) +
							Math.abs(rgb[1] - neighbor.rgb[1]) +
							Math.abs(rgb[2] - neighbor.rgb[2]);
			diff = temp;
		}
		return diff;
	}

	int getId() {
		return id;
	}

	int[] getRGB() {
		return rgb;
	}

	@Override
	public String toString() {
		return "Person: " + id;
	}
}
