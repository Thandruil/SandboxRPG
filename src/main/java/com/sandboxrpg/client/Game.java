package com.sandboxrpg.client;

import com.sandboxrpg.world.World;
import com.sun.deploy.util.BufferUtil;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;
import java.security.Key;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Game {

    public static final Logger logger = Logger.getLogger("");
    private static Game theGame;

    boolean running = true;

    int displayWidth;
    int displayHeight;
    float aspectRatio;
    boolean fullscreen;

    float speed = 0.2f;

    Vector3f camerapos = new Vector3f(0,0,0);
    Vector3f camerarot = new Vector3f(0,0,0);

    public Game(int width, int height, boolean fullscreen) {
        theGame = this;
        this.displayWidth = width;
        this.displayHeight = height;
        this.aspectRatio = (float) width/height;
        this.fullscreen = fullscreen;
    }

    void initGame() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));

        Display.setResizable(false);
        Display.setTitle("SandboxRPG 0.0.1-SNAPSHOT");

        Display.create();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluPerspective(90f, 1280f/720f, 0.1f, 500f);

        glMatrixMode(GL_MODELVIEW);
        glClearColor(0, 0.75f, 1, 1);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
    }

    public void run() {
        this.running = true;

        Mouse.setGrabbed(true);

        try {
            initGame();
        } catch (LWJGLException e) {
            logger.log(Level.SEVERE, null, e);
        }

        World world = new World();

        while (!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            glRotatef(camerarot.x, 1, 0, 0);
            glRotatef(camerarot.y, 0, 1, 0);
            glTranslatef(-camerapos.x, -camerapos.y, -camerapos.z);

            world.render();

            if (Mouse.isGrabbed()) {
                float dx = Mouse.getDX() * 0.16f;
                float dy = Mouse.getDY() * 0.16f;

                if (camerarot.y + dx >= 360) {
                    camerarot.y = camerarot.y + dx - 360;
                } else if (camerarot.y + dx < 0) {
                    camerarot.y = 360 - camerarot.y + dx;
                } else {
                    camerarot.y += dx;
                }

                if (camerarot.x - dy >= -90 && camerarot.x - dy <= 90) {
                    camerarot.x -= dy;
                } else if (camerarot.x - dy < -90) {
                    camerarot.x = -90;
                } else if (camerarot.x - dy > 90) {
                    camerarot.x = 90;
                }
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
                camerapos.x -= speed*Math.sin(Math.toRadians(-camerarot.y));
                camerapos.z -= speed*Math.cos(Math.toRadians(-camerarot.y));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
                camerapos.x += speed*Math.sin(Math.toRadians(-camerarot.y));
                camerapos.z += speed*Math.cos(Math.toRadians(-camerarot.y));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
                camerapos.x += speed*Math.sin(Math.toRadians(-camerarot.y - 90));
                camerapos.z += speed*Math.cos(Math.toRadians(-camerarot.y - 90));
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
                camerapos.x += speed*Math.sin(Math.toRadians(-camerarot.y + 90));
                camerapos.z += speed*Math.cos(Math.toRadians(-camerarot.y + 90));
            }

            camerapos.y = world.getHeight(camerapos.x, camerapos.z) + 1.6f;

            if (camerapos.x >= world.getSize()-1) {camerapos.x = world.getSize()-1;}
            if (camerapos.z >= world.getSize()-1) {camerapos.z = world.getSize()-1;}
            if (camerapos.x < 1) {camerapos.x = 1;}
            if (camerapos.z < 1) {camerapos.z = 1;}

            Display.update();
            Display.sync(60);
        }
    }

    public static void main(String[] args) {
        new Game(1280, 720, false).run();
    }
}
