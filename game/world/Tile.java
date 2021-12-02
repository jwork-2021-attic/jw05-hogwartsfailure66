package world;

import asciiPanel.AsciiPanel;

import java.awt.*;
/*
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

/**
 * @author Aeranythe Echosong
 */
public enum Tile {

    FLOOR((char) 0, AsciiPanel.black),

    BEAN((char) 250, AsciiPanel.brightYellow),

    WALL((char) 177, AsciiPanel.brightBlack),

    PLAYER((char) 2, AsciiPanel.brightGreen),

    GHOST((char) 1, AsciiPanel.brightBlue),

    HEART((char) 3, AsciiPanel.brightRed);

    private char glyph;

    public char glyph() {
        return glyph;
    }

    private Color color;

    public Color color() {
        return color;
    }

    public boolean isPassable() {
        return this != Tile.WALL;
    }

    Tile(char glyph, Color color) {
        this.glyph = glyph;
        this.color = color;
    }
}
