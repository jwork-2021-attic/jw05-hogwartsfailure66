package world;

import data.CommonData;
import data.Direction;

public class Player extends Object implements Runnable {
    public int life;
    public int score;

    public Player(int speed, Map map, World world, int score) {
        life = 3;
        x = CommonData.PLAYER_START_X;
        y = CommonData.PLAYER_START_Y;
        this.world = world;
        this.map = map;
        this.speed = speed;
        this.sleepTime = 1000 / speed * 50;
        direction = Direction.STAY;
        this.score = score;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public void addLife() {
        if (life < CommonData.MAX_LIFE) {
            life++;
        }
    }

    public boolean minusLife() {
        life--;
        if (life == 0) {
            return false;
        }
        return true;
    }

    public void addScore() {
        score += CommonData.BEAN_VALUE;
    }

    public int getScore() {
        return score;
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
        while (getLife() > 0 && !world.winFlag && !world.loseFlag) {
//            System.out.println("player run");
//            if (score >= world.getWinScore()) {
//                world.winFlag = true;
//                System.out.println("WIN");
//                break;
//            }
            if (direction != Direction.STAY) {
                System.out.println("player x:" + x + ", y:" + y + ", direction:" + direction);
                Tile nextTile = getNextTile(x, y, direction);
                if (canMove(nextTile)) {
                    try {
                        world.setTile(x, y, direction, nextTile, this, null);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    direction = Direction.STAY;
                }
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {

            }
        }
    }
}
