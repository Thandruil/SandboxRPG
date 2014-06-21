package com.sandboxrpg.world;

import com.sandboxrpg.entities.Entity;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class World {

    private final static int scale = 4;
    private float[][] heightmap;
    private int[][] tilemap;

    private ArrayList<Entity> entities;

    public World() {
        entities = new ArrayList<Entity>();
        
        loadHeightmap();
        generate();
    }

    void loadHeightmap() {
        try {
            BufferedImage heightmapImage = ImageIO.read(new File("resources/heightmap256.bmp")); // 64x or 256x
            heightmap = new float[heightmapImage.getWidth()][heightmapImage.getHeight()];

            Color color;
            for (int x = 0; x < heightmap.length; x++) {
                for (int z = 0; z < heightmap[x].length; z++) {
                    color = new Color(heightmapImage.getRGB(x, z));
                    heightmap[x][z] = color.getRed();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.add(entity);
    }

    void generate() {
        tilemap = new int[heightmap.length][heightmap.length];
        Random random = new Random();
        for (int x = 0; x < tilemap.length; x++) {
            for (int z = 0; z < tilemap[x].length; z++) {
                tilemap[x][z] = random.nextInt(4);
            }
        }
    }

    public void dig(float x, float z) {
        heightmap[Math.round(x/scale)][Math.round(z/scale)] = heightmap[Math.round(x/scale)][Math.round(z/scale)] - 1f;
    }

    public int getSize() {
        return (heightmap.length - 1) * scale;
    }

    public float getScale() {
        return scale;
    }

    public float[][] getHeightmap() {
        return heightmap;
    }

    public int[][] getTilemap() { return tilemap; }

    public float getHeight(float x, float z) {
        int vectorX = Math.round(x - (x % scale));
        int vectorZ = Math.round(z - (z % scale));

        Vector3f A = new Vector3f(vectorX, heightmap[vectorX / scale][vectorZ / scale], vectorZ);
        Vector3f C = new Vector3f(vectorX + scale, heightmap[vectorX / scale + 1][vectorZ / scale + 1], vectorZ + scale);

        Vector3f B;
        if (x % scale > z % scale) {
            B = new Vector3f(vectorX + scale, heightmap[vectorX / scale + 1][vectorZ / scale], vectorZ);
        } else {
            B = new Vector3f(vectorX, heightmap[vectorX / scale][vectorZ / scale + 1], vectorZ + scale);
        }

        Vector3f AB = new Vector3f(B.x - A.x, B.y - A.y, B.z - A.z);
        Vector3f AC = new Vector3f(C.x - A.x, C.y - A.y, C.z - A.z);

        float a = (AB.y * AC.z) - (AB.z * AC.y);
        float b = (AB.z * AC.x) - (AB.x * AC.z);
        float c = (AB.x * AC.y) - (AB.y * AC.x);

        float d = -(a * A.x + b * A.y + c * A.z);

        return (-d - a * x - c * z) / b;
    }
}
