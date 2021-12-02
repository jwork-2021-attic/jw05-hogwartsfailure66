package world;

import data.Direction;

public class Object {
    public int x;
    public int y;
    public int speed;
    public int sleepTime;
    public Map map;
    public World world;
    public Direction direction;
    public int id;

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Tile getNextTile(int x, int y, Direction direction) {
        return world.getTile(x + direction.getDx(), y + direction.getDy());
    }

    public boolean canMove(Tile tile) {
        return tile.isPassable();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
