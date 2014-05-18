package com.sandboxrpg.world;

import org.lwjgl.util.vector.Vector3f;

import java.util.Random;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

public class World {

    float[][] heightmap = new float[33][33];
    int[][] tilemap = new int[32][32];

    float color1;
    float color2;
    float color3;

    final static int scale = 4;

    Random random;

    public World() {
        random = new Random();

        generate();

        color1 = random.nextFloat();
        color2 = random.nextFloat();
        color3 = random.nextFloat();
    }

    void generate() {
        for (int x=0; x<33; x++) {
            for (int z=0; z<33; z++) {
                heightmap[x][z] = random.nextFloat();
            }
        }
    }

    public void render() {
        for (int x=0; x<32; x++) {
            for (int z=0; z<32; z++) {
                glBegin(GL_TRIANGLES);
                    glColor3f(color1, color2, color3);
                    // A
                    glVertex3f(x*scale, heightmap[x][z], z*scale);
                    // B
                    glVertex3f((x+1)*scale, heightmap[x+1][z], z*scale);
                    // C
                    glVertex3f((x+1)*scale, heightmap[x+1][z+1], (z+1)*scale);

                    // A
                    glVertex3f(x*scale, heightmap[x][z], z*scale);
                    // C
                    glVertex3f((x+1)*scale, heightmap[x+1][z+1], (z+1)*scale);
                    // D
                    glVertex3f(x*scale, heightmap[x][z+1], (z+1)*scale);
                glEnd();

                glBegin(GL_LINE_LOOP);
                    glColor3f(0,0,0);
                    // A
                    glVertex3f(x*scale, heightmap[x][z], z*scale);
                    // B
                    glVertex3f((x+1)*scale, heightmap[x+1][z], z*scale);
                    // C
                    glVertex3f((x+1)*scale, heightmap[x+1][z+1], (z+1)*scale);

                    // A
                    glVertex3f(x*scale, heightmap[x][z], z*scale);
                    // C
                    glVertex3f((x+1)*scale, heightmap[x+1][z+1], (z+1)*scale);
                    // D
                    glVertex3f(x*scale, heightmap[x][z+1], (z+1)*scale);
                glEnd();
            }
        }
    }

    public float getHeight(float x, float z) {
        int vectorX = Math.round(x - (x % scale));
        int vectorZ = Math.round(z - (z % scale));

        // Create all the vectors on the quad
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

        Vector3f cross = new Vector3f();
        Vector3f.cross(AB, AC, cross);

        float a = cross.x;
        float b = cross.y;
        float c = cross.z;

        float d = -(a*A.x+b*A.y+c*A.z);

        //System.out.println(a + "x + " + b + "y + " + c + "z + " + d + " = 0");

        return (-d-a*x-c*z)/b;
    }
}
