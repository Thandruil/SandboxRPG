package com.sandboxrpg.client.render;

import com.sandboxrpg.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class WorldRenderer {

    Color[] tiles = {
            Color.ORANGE,
            Color.GREEN,
            Color.GRAY,
            Color.BLUE
    };

    public WorldRenderer() {
    }

    public void render(World world) {
        float[][] heightmap = world.getHeightmap();
        int[][] tilemap = world.getTilemap();
        float scale = world.getScale();

        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int x=0; x<heightmap.length-1; x++) {
            for (int z=0; z<heightmap[x].length-1; z++) {

                GL11.glColor3f(tiles[tilemap[x][z]].getRed(), tiles[tilemap[x][z]].getGreen(), tiles[tilemap[x][z]].getBlue());
                GL11.glVertex3f(x * scale, heightmap[x][z], z * scale);
                GL11.glVertex3f((x + 1) * scale, heightmap[x + 1][z], z * scale);
                GL11.glVertex3f((x + 1) * scale, heightmap[x + 1][z + 1], (z + 1) * scale);

                GL11.glVertex3f(x*scale, heightmap[x][z], z*scale);
                GL11.glVertex3f((x+1)*scale, heightmap[x+1][z+1], (z+1)*scale);
                GL11.glVertex3f(x*scale, heightmap[x][z+1], (z+1)*scale);

            }
        }
        GL11.glEnd();

    }
}
