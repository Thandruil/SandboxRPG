package com.sandboxrpg.client.render;

import com.sandboxrpg.world.Heightmap;
import com.sandboxrpg.world.Tilemap;
import com.sandboxrpg.world.World;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class WorldRenderer {

    public WorldRenderer() {
    }

    public void render(World world) {
        Heightmap heightmap = world.getHeightmap();
        Tilemap tilemap = world.getTilemap();
        float scale = world.getScale();

        GL11.glBegin(GL11.GL_TRIANGLES);
        for (int x = 0; x < heightmap.getLength() - 1; x++) {
            for (int z = 0; z < heightmap.getWidth(x) - 1; z++) {

                GL11.glColor3f(0, 1, 1);
                GL11.glVertex3f(x * scale, heightmap.getHeight(x, z), z * scale);
                GL11.glVertex3f((x + 1) * scale, heightmap.getHeight(x + 1, z), z * scale);
                GL11.glVertex3f(x * scale, heightmap.getHeight(x, z + 1), (z + 1) * scale);

                GL11.glVertex3f((x + 1) * scale, heightmap.getHeight(x + 1, z), z * scale);
                GL11.glVertex3f(x * scale, heightmap.getHeight(x, z + 1), (z + 1) * scale);
                GL11.glVertex3f((x + 1) * scale, heightmap.getHeight(x + 1, z + 1), (z + 1) * scale);

            }
        }
        GL11.glEnd();

    }
}
