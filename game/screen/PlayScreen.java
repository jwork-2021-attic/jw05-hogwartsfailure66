package screen;

import asciiPanel.AsciiPanel;
import data.CommonData;
import data.Direction;
import world.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayScreen implements Screen {
    public World world;
    public Map map;
    public Player player;
    public ApplicationMain mainFrame;
    public ExecutorService executorService;
    public List<Ghost> ghosts;

    public PlayScreen(ApplicationMain mainFrame) {
        world = new World();
        map = new Map();
        player = new Player(500, map, world, 0);
        ghosts = new ArrayList<Ghost>();

        if (world.tiles == null) {
            try {
                Tile[][] tmp = map.parseMap("../resources/board.txt");
                world.setTiles(tmp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < 4; i++) {
            int startX = CommonData.GHOST_START_X[i];
            int startY = CommonData.GHOST_START_Y[i];
            Ghost ghost = new Ghost(1, 100, map, world, startX, startY, Tile.FLOOR, i);
            System.out.println(ghost.id);
            ghosts.add(ghost);
        }
        world.setWinScore(map.getBeanCount());
//        System.out.println(world.winScore);
        world.setPlayScreen(this);
        world.setPlayer(player);
        world.setGhosts(ghosts);
        this.mainFrame = mainFrame;
        play();
    }

    public void setMainFrame(ApplicationMain mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void play() {
        executorService = Executors.newCachedThreadPool();
        executorService.execute(new Thread(player));
//        for (Ghost ghost : ghosts) {
//            executorService.execute(new Thread(ghost));
//        }
        System.out.println("ghost num " + ghosts.size());
        executorService.execute(new Thread(ghosts.get(0)));
        executorService.execute(new Thread(ghosts.get(1)));
        executorService.execute(new Thread(ghosts.get(2)));
        executorService.execute(new Thread(ghosts.get(3)));
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {
        terminal.write("Score:" + player.getScore(), 0, 0);
        terminal.write("Life:", 12, 0);
        int life = player.getLife();
        for (int i = 0; i < life; i++) {
            terminal.write(Tile.HEART.glyph(), 17 + i, 0, Tile.HEART.color());
        }
//        Tile[][] tiles = world.getTiles();
//        System.out.println(tiles.length);
//        System.out.println(tiles[0].length);
        for (int i = 0; i < CommonData.WORLD_WIDTH; i++) {
            for (int j = 0; j < CommonData.WORLD_HEIGHT; j++) {
                Tile tile = world.getTileAt(i, j);
                terminal.write(tile.glyph(), i, j + CommonData.DISPLAY_Y_OFFSET, tile.color());
            }
        }
    }

    @Override
    public Screen respondToUserInput(KeyEvent key) {
//        System.out.println("pressed");
        switch (key.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                player.setDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                player.setDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                player.setDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                player.setDirection(Direction.RIGHT);
                break;
        }
        return this;
    }
}
