package world;

import data.Direction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Map {
    public Tile[][] gameMap;
    public int beanCount;

    public Map() {
        gameMap = null;
        beanCount = 0;
    }

    public Tile[][] parseMap(char[][] map) {
        int width = map.length;
        int height = map[0].length;
        int beanCount = 0;

        Tile[][] tmp = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                switch (map[i][j]) {
                    case '#':
                        tmp[i][j] = Tile.WALL;
                        break;
                    case '.':
                        tmp[i][j] = Tile.BEAN;
                        beanCount++;
                        break;
                    case 'G':
                        System.out.println("ghost pos:" + i + " " + j);
                        tmp[i][j] = Tile.FLOOR;
                        break;
                    case 'P':
                        tmp[i][j] = Tile.PLAYER;
                        break;
                    case '*':
                        tmp[i][j] = Tile.HEART;
                        break;
                    default:
                        tmp[i][j] = Tile.FLOOR;
                }
            }
        }
        this.beanCount = beanCount;
        gameMap = tmp;
        return tmp;
    }

    public Tile[][] parseMap(List<String> text) {

        int height = text.size();
        int width = text.get(0).length();

        char[][] map = new char[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map[x][y] = text.get(y).charAt(x);
            }
        }
        return parseMap(map);
    }

    public Tile[][] parseMap(InputStream source) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                source, "UTF-8"))) {
            List<String> lines = new ArrayList<>();
            while (reader.ready()) {
                lines.add(reader.readLine());
            }
            return parseMap(lines);
        }
    }

    public Tile[][] parseMap(String mapName) throws IOException {
        try (InputStream boardStream = Map.class.getResourceAsStream(mapName)) {
            if (boardStream == null) {
                throw new IOException("Could not get resource for: " + mapName);
            }
            return parseMap(boardStream);
        }
    }

    public int getBeanCount() {
        return beanCount;
    }

}
