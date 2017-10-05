package mainpackage;

import javax.swing.*;
import java.awt.*;

public class FinalPanel extends JPanel {

    private Person[][] people;
    private int rows;
    private int cols;

    FinalPanel (int rows, int cols, Person[][] people, JPanel parent) {
        this.people = people;
        this.rows = rows;
        this.cols = cols;
        setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight()));
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = Utils.getInstance().getWidth();
        int height = Utils.getInstance().getHeight();
        int currentX = 0;
        int currentY = 0;
        System.out.println();
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
