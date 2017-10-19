package mainpackage;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                setup();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private static void setup() throws InterruptedException {
        Utils utils = Utils.getInstance();
        int nThreads = 256;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        frame.setSize(new Dimension(900, 700));
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        JPanel mainPanel = new JPanel();
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        CountDownLatch latch = new CountDownLatch(1);
        Classroom classroom = new Classroom(utils.getRows(), utils.getCols());
        Exchanger<Classroom> exchanger = new Exchanger<>();
        ExecutorService service = Executors.newFixedThreadPool(nThreads);

        for (int i = 0; i < nThreads; i++) {
            service.submit(new CRunnable(classroom, exchanger, latch, mainPanel));
        }

        latch.await();

        System.out.println("Done");

        mainPanel.removeAll();
        mainPanel.add(new FinalPanel(utils.getRows(), utils.getCols(), utils.getBest().getPeople(), mainPanel));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
