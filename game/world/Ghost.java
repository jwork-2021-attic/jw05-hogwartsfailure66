package world;

import data.Direction;

import java.util.List;
import java.util.Random;

public class Ghost extends Object implements Runnable {
    public Tile oldTile;
    public int level;
    public int startX;
    public int startY;

    public Ghost(int level, int speed, Map map, World world, int startX, int startY, Tile oldTile, int id) {
        this.level = level;
        this.speed = speed;
        this.map = map;
        this.world = world;
        this.startX = startX;
        this.startY = startY;
        this.x = startX;
        this.y = startY;
        this.sleepTime = 1000 / (level * speed) * 50;
        direction = Direction.STAY;
        this.oldTile = oldTile;
        this.id = id;
    }

    public Tile getOldTile() {
        return oldTile;
    }

    public void setOldTile(Tile oldTile) {
        this.oldTile = oldTile;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void getRandomDirection() {
        Random random = new Random();
        List<Direction> possibleDirections = world.getPassableDirections(x, y);
        direction = possibleDirections.get(random.nextInt(1000) % possibleDirections.size());
    }

    public void aStar() {
        // TO BE DONE
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
        while (!world.winFlag && !world.loseFlag) {
            getRandomDirection();
            Tile nextTile = getNextTile(x, y, direction);
            if (canMove(nextTile)) {
                try {
                    world.setTile(x, y, direction, nextTile, null, this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("GHOST NEXT POSSIBLE TILE ERROR!!!");
                System.exit(1);
            }

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
        }
    }
}
