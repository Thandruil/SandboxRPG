package com.sandboxrpg.world;

import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Heightmap {

    float[][] height;

    public Heightmap(String filename) {
        try {
            BufferedImage image = ImageIO.read(new File(filename));

            height = new float[image.getWidth()][image.getHeight()];
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    height[x][y] = (new Color(image.getRGB(x, y)).getRed());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLength() {
        return height.length;
    }

    public int getWidth(int x) {
        return height[x].length;
    }

    public float getHeight(int x, int z) {
        return height[x][z];
    }

    public float getHeight(float x, float z) {
        int floorX = (int) x;
        int floorZ = (int) z;

        Vector3f p0 = new Vector3f(floorX, getHeight(floorX, floorZ), floorZ);
        Vector3f p1 = new Vector3f(floorX + 1, getHeight(floorX + 1, floorZ), floorZ);
        Vector3f p2 = new Vector3f(floorX, getHeight(floorX, floorZ + 1), floorZ + 1);
        Vector3f p3 = new Vector3f(floorX + 1, getHeight(floorX + 1, floorZ + 1), floorZ + 1);

        if (x + z <= 1) {
            return p0.y + (p2.y - p0.y) * (z - p0.z) + (p1.y - p0.y) * (x - p0.x);
        } else {
            return p3.y - (p2.y - p3.y) * (z - p3.z) - (p1.y - p3.y) * (x - p3.x);
        }
    }
}
