package com.sandboxrpg.client.gui;

import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiUtils {
    public static void drawRectangle(int x1, int x2, int y1, int y2, Color color) {
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glColor3f(color.getRed(), color.getGreen(), color.getBlue());
        GL11.glVertex2i(x1, y1);
        GL11.glVertex2i(x2, y1);
        GL11.glVertex2i(x2, y2);
        GL11.glVertex2i(x1, y2);
        GL11.glEnd();
    }
}
