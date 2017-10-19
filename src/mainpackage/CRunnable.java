package mainpackage;

import javax.swing.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

public class CRunnable implements Runnable {

    private Classroom best;
    private final int iterations = 50;
    private Exchanger<Classroom> exchanger;
    private static boolean shutdown = false;
    private JPanel mainPanel;
    private CountDownLatch latch;

    CRunnable(Classroom classroom, Exchanger<Classroom> exchanger, CountDownLatch latch, JPanel mainPanel) {
        best = classroom;
        this.exchanger = exchanger;
        this.mainPanel = mainPanel;
        this.latch = latch;
    }

    public void run() {
        for (int i = 0; !shutdown && i < iterations; i++) {
            Classroom temp = best;
            if (Utils.getInstance().findDifferent(best.getPeople()) > 0) {
                temp.setPeople(Utils.getInstance().rearrange(temp.getPeople()));
                while (Utils.getInstance().calcAffinity(temp.getPeople())
                        < Utils.getInstance().calcAffinity(best.getPeople())) {
                    temp.setPeople(Utils.getInstance().rearrange(temp.getPeople()));
                }
                temp.draw(mainPanel.getGraphics());
                best.setPeople(temp.getPeople());
                exchange();
            } else {
                stop();
            }
        }
        Utils.getInstance().setBest(best);
        latch.countDown();
    }

    private void stop() {
        try {
            shutdown = true;
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void exchange() {
        try {
            best = exchanger.exchange(best);
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
