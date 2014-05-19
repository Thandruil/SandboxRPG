package com.sandboxrpg.world;

import com.sandboxrpg.client.Game;
import org.lwjgl.util.vector.Vector3f;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

public class World {

    float[][] heightmap;

    float color1;
    float color2;
    float color3;

    final static int scale = 4;

    Random random;

    public World() {
        random = new Random();

        loadHeightmap();
        //generate();

        color1 = random.nextFloat();
        color2 = random.nextFloat();
        color3 = random.nextFloat();
    }

    void loadHeightmap() {
        try {
            BufferedImage heightmapImage = ImageIO.read(new File("res/heightmap64.bmp")); // 64x or 256x
            heightmap = new float[heightmapImage.getWidth()][heightmapImage.getHeight()];

            Color color;
            for (int x=0; x<heightmap.length; x++) {
                for (int z=0; z<heightmap[x].length; z++) {
                    color = new Color(heightmapImage.getRGB(x, z));
                    heightmap[x][z] = color.getRed()/16f;
                }
            }
        } catch (IOException e) {
            Game.logger.severe("Heightmap file not found!");
        }
    }

    void generate() {
        heightmap = new float[64][64];
        for (int x=0; x<heightmap.length; x++) {
            for (int z=0; z<heightmap[x].length; z++) {
                heightmap[x][z] = random.nextFloat();
            }
        }
    }

    public int getSize() {
        return (heightmap.length-1) * scale;
    }

    public void render() {
        for (int x=0; x<heightmap.length-1; x++) {
            glBegin(GL_TRIANGLE_STRIP);
            glColor3f(color1, color2, color3);
            for (int z=0; z<heightmap[x].length; z++) {
                glVertex3f(x*scale, heightmap[x][z], z*scale);
                glVertex3f((x+1)*scale, heightmap[x+1][z], z*scale);
            }
            glEnd();
        }
    }

    public float getHeight(float x, float z) {
        int vectorX = Math.round(x - (x % scale));
        int vectorZ = Math.round(z - (z % scale));

        Vector3f A = new Vector3f(vectorX , heightmap[vectorX/scale][vectorZ/scale], vectorZ);
        Vector3f C = new Vector3f(vectorX + scale, heightmap[vectorX/scale + 1][vectorZ/scale + 1], vectorZ + scale);

        Vector3f B;
        if (x%scale>z%scale) {
            B = new Vector3f(vectorX + scale, heightmap[vectorX/scale + 1][vectorZ/scale], vectorZ);
        } else {
            B = new Vector3f(vectorX, heightmap[vectorX/scale][vectorZ/scale + 1], vectorZ + scale);
        }

        Vector3f AB = new Vector3f(B.x-A.x, B.y-A.y, B.z-A.z);
        Vector3f AC = new Vector3f(C.x-A.x, C.y-A.y, C.z-A.z);

        float a = (AB.y*AC.z)-(AB.z*AC.y);
        float b = (AB.z*AC.x)-(AB.x*AC.z);
        float c = (AB.x*AC.y)-(AB.y*AC.x);

        float d = -(a*A.x+b*A.y+c*A.z);

        return (-d-a*x-c*z)/b;
    }
}
