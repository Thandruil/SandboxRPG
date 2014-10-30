package com.sandboxrpg.world;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tilemap {

    int[][] tile;

    public Tilemap(String filename) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));

            tile = new int[image.getWidth()][image.getHeight()];
            for (int x = 0; x < tile.length; x++) {
                for (int y = 0; y < tile[x].length; y++) {
                    tile[x][y] = (new Color(image.getRGB(x, y))).getRed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getSize() {
        return tile.length;
    }

    public int getTile(int x, int z) {
        return tile[x][z];
    }
}
