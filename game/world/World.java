package world;

import data.CommonData;
import data.Direction;
import screen.LoseScreen;
import screen.PlayScreen;
import screen.Screen;
import screen.WinScreen;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class World {
    public Tile[][] tiles;
    public int width;
    public int height;
    public int winScore;
    public boolean winFlag;
    public boolean loseFlag;
    public Lock lock;
    public Player player;
    public List<Ghost> ghosts;
    public PlayScreen playScreen;
    public Direction[] directions = {Direction.UP, Direction.RIGHT, Direction.DOWN, Direction.LEFT};

    public World() {
        width = CommonData.WORLD_WIDTH;
        height = CommonData.WORLD_HEIGHT;
        winFlag = false;
        loseFlag = false;
        lock = new ReentrantLock();
    }

    public void setWinScore(int beanCount) {
        winScore = CommonData.BEAN_VALUE * beanCount;
    }

    public int getWinScore() {
        return winScore;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTileAt(int x, int y) {
        return tiles[x][y];
    }

    public List<Direction> getPassableDirections(int x, int y) {
        List<Direction> list = new ArrayList<Direction>();
        for (Direction d : directions) {
            Tile tile = tiles[x + d.getDx()][y + d.getDy()];
            if (tile.isPassable()) {
                list.add(d);
            }
        }
        return list;
    }

    public void setGhosts(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }

    public PlayScreen getPlayScreen() {
        return playScreen;
    }

    public void setPlayScreen(PlayScreen playScreen) {
        this.playScreen = playScreen;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void movePlayer(int x, int y, Direction direction, Object object) {
        tiles[x][y] = Tile.FLOOR;
        object.setX(x + direction.getDx());
        object.setY(y + direction.getDy());
        tiles[x + direction.getDx()][y + direction.getDy()] = Tile.PLAYER;
        object.setDirection(Direction.STAY);
    }

    public void moveGhost(int x, int y, Direction direction, Ghost ghost) {
        System.out.println("ghost:" + ghost.id + " pos:" + x + " " + y + " direction:" + direction + " oldtile:" + ghost.oldTile);
        if (ghost.oldTile == Tile.PLAYER || ghost.oldTile == Tile.GHOST) {
            System.out.println("GHOST OLD TILE ERROR");
            System.exit(1);
        }
        if (tiles[x + direction.getDx()][y + direction.getDy()] != Tile.GHOST) {
            tiles[x][y] = ghost.oldTile;
            ghost.setX(x + direction.getDx());
            ghost.setY(y + direction.getDy());
            if (tiles[x + direction.getDx()][y + direction.getDy()] == Tile.PLAYER) {
                ghost.oldTile = Tile.FLOOR;
            } else {
                ghost.oldTile = tiles[x + direction.getDx()][y + direction.getDy()];
            }

            tiles[x + direction.getDx()][y + direction.getDy()] = Tile.GHOST;
        }
    }

    public void resetPlayer() {
        tiles[player.getX()][player.getY()] = Tile.FLOOR;
        player.setDirection(Direction.STAY);
        player.setX(CommonData.PLAYER_START_X);
        player.setY(CommonData.PLAYER_START_Y);
        tiles[CommonData.PLAYER_START_X][CommonData.PLAYER_START_Y] = Tile.PLAYER;
    }

    public void resetGhosts() {
        for (Ghost ghost : ghosts) {
            if(ghost.oldTile==Tile.PLAYER) {
                tiles[ghost.getX()][ghost.getY()] = Tile.FLOOR;
            }
            else{
                tiles[ghost.getX()][ghost.getY()] = ghost.oldTile;
            }
            ghost.oldTile = Tile.FLOOR;
            ghost.setDirection(Direction.STAY);
            int startX = ghost.getStartX();
            int startY = ghost.getStartY();
            ghost.setX(startX);
            ghost.setY(startY);
            //tiles[startX][startY] = Tile.GHOST;
        }
    }

    public void resetPlayerGhosts() {
        resetPlayer();
        resetGhosts();
    }

    public void playerHitGhost(int x, int y) throws InterruptedException {
        if (player.minusLife()) {
            // RESET PLAYER AND GHOSTS;
            tiles[x][y] = Tile.FLOOR;
            TimeUnit.MILLISECONDS.sleep(200);
            resetPlayerGhosts();
            System.out.println("LIFE-1, WAIT 1s");
            playScreen.mainFrame.repaint();
            TimeUnit.SECONDS.sleep(1);
        } else {
            // LOSE
            tiles[x][y] = Tile.FLOOR;
            loseFlag = true;
            System.out.println("LOSE");
            playScreen.mainFrame.repaint();
            TimeUnit.MILLISECONDS.sleep(100);
            playScreen.mainFrame.setScreen(new LoseScreen(playScreen.mainFrame));
            playScreen.mainFrame.repaint();
        }
    }

    public synchronized void setTile(int x, int y, Direction direction, Tile nextTile, Player player, Ghost ghost) throws InterruptedException {
        lock.lock();
        if (player != null) {
            switch (nextTile) {
                case GHOST:
                    System.out.println("player hit ghost");
                    playerHitGhost(x, y);
                    break;
                case BEAN:
                    player.addScore();
                    movePlayer(x, y, direction, player);
                    if (player.getScore() >= winScore) {
                        winFlag = true;
                        System.out.println("WIN");
                        playScreen.mainFrame.repaint();
                        TimeUnit.MILLISECONDS.sleep(100);
                        playScreen.mainFrame.setScreen(new WinScreen(playScreen.mainFrame));
                        playScreen.mainFrame.repaint();
                    }
                    break;
                case HEART:
                    player.addLife();
                    movePlayer(x, y, direction, player);
                    break;
                case FLOOR:
                    movePlayer(x, y, direction, player);
                    break;
            }
        } else if (ghost != null) {
            switch (nextTile) {
                case PLAYER:
                    System.out.println("ghost hit player");
                    moveGhost(x, y, direction, ghost);
                    playerHitGhost(x + direction.getDx(), y + direction.getDy());
                    break;
                case GHOST:
                    // do not move
                    break;
                case FLOOR:
                case BEAN:
                case HEART:
                    moveGhost(x, y, direction, ghost);
                    break;
            }
        }
        lock.unlock();
    }
}
