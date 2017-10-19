package mainpackage;

import java.awt.*;

class Classroom {

    private Person[][] people;
    private int rows;
    private int cols;

    Classroom(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        people = new Person[rows][cols];
        generatePopulation();
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

    Person[][] getPeople() {
        return people;
    }

    void setPeople(Person[][] people) {
        this.people = people;
    }

    void draw(Graphics g) {
        int width = Utils.getInstance().getWidth();
        int height = Utils.getInstance().getHeight();
        int currentX = 0;
        int currentY = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Person p = people[i][j];
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
