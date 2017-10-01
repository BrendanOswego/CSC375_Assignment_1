package mainpackage;

public class Main {

	public static void main (String[] args) {
		setup();
	}

	private static void setup () {
		Population pop = new Population(8,4);
		pop.generatePopulation();
	}
}
