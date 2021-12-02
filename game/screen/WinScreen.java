package screen;

import asciiPanel.AsciiPanel;
import world.ApplicationMain;

public class WinScreen extends RestartScreen{
    public WinScreen(ApplicationMain mainFrame) {
        super(mainFrame);
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("YOU WIN!!!", 0, 0);
        terminal.write("PRESS ENTER TO RESTART", 0, 1);
    }
}
