package mainpackage;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class Utils {

    private static Utils utils = null;
    private Classroom best;
    private static volatile boolean set = false;
    private final static int rows = 12;
    private final static int cols = 10;
    private final static int width = 50;
    private final static int height = 50;

    static Utils getInstance() {
        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }

    int calcAffinity(Person[][] people) {
        int affinity = 0;
        for (int i = 0; i < people.length; i++) {
            for (int j = 0; j < people[i].length; j++) {
                affinity += people[i][j].calcAffinity();
            }
        }
        return affinity;
    }

    Person[][] rearrange(Person[][] people) {
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

    int getRows() {
        return rows;
    }

    int getCols() {
        return cols;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    synchronized void setBest(Classroom best) {
        if (this.best == null) this.best = best;
        else {
            if (calcAffinity(this.best.getPeople()) > calcAffinity(best.getPeople())) {
                this.best = best;
            }
        }
    }

    Classroom getBest() {
        return best;
    }

}
