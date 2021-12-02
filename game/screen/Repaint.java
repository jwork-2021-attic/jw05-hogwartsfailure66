package screen;

import javax.swing.*;

public class Repaint implements Runnable {
    public JFrame mainFrame;
    public Screen screen;

    public Repaint(JFrame mainFrame, Screen screen) {
        this.mainFrame = mainFrame;
        this.screen = screen;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            mainFrame.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
    }
}
