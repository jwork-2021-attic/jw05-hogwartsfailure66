package screen;

import asciiPanel.AsciiPanel;
import world.ApplicationMain;

public class LoseScreen extends RestartScreen {
    public LoseScreen(ApplicationMain mainFrame) {
        super(mainFrame);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("YOU LOSE...", 0, 0);
        terminal.write("PRESS ENTER TO RESTART", 0, 1);
    }
}
