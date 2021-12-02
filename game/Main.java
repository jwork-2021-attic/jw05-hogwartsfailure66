import world.ApplicationMain;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ApplicationMain app = new ApplicationMain();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setResizable(false);
        app.setVisible(true);
        app.setLocationRelativeTo(null);
    }
}
