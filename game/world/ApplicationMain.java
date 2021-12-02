package world;/*
 * Copyright (C) 2015 Aeranythe Echosong
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiFont;
import asciiPanel.AsciiPanel;
import data.CommonData;
import screen.Repaint;
import screen.Screen;
import screen.StartScreen;
import world.World;

/**
 * @author Aeranythe Echosong
 */
public class ApplicationMain extends JFrame implements KeyListener {

    private AsciiPanel terminal;
    private Screen screen;

    public ApplicationMain() {
        super();
        terminal = new AsciiPanel(CommonData.SCREEN_WIDTH, CommonData.SCREEN_HEIGHT, AsciiFont.TALRYTH_15_15);
        add(terminal);
        pack();
        screen = new StartScreen(this);
        Thread t = new Thread(new Repaint(this, screen));
        t.start();
        addKeyListener(this);
        repaint();
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void repaint() {
        terminal.clear();
        screen.displayOutput(terminal);
        super.repaint();
    }

    /**
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        screen = screen.respondToUserInput(e);
        repaint();
    }

    /**
     * @param e
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * @param e
     */
    public void keyTyped(KeyEvent e) {
    }

}
