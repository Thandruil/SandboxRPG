package com.sandboxrpg.client;

import com.sandboxrpg.client.render.Camera;
import com.sandboxrpg.client.render.WorldRenderer;
import com.sandboxrpg.entities.Entity;
import com.sandboxrpg.player.Player;
import com.sandboxrpg.world.World;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Game {

    public static final Logger logger = Logger.getLogger("");

    private boolean running = true;

    private int displayWidth;
    private int displayHeight;
    private float aspectRatio;

    private World currentWorld;
    private WorldRenderer worldRenderer;

    private Camera camera;
    private Player player;

    private float delta;

    private Game() {
        this.displayWidth = 1500;
        this.displayHeight = 720;
        this.aspectRatio = (float) displayWidth / displayHeight;
    }

    public static void main(String[] args) {
        new Game().run();
    }

    void initGame() throws LWJGLException {
        Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));

        Display.setResizable(false);
        Display.setTitle("SandboxRPG 0.0.1-SNAPSHOT");

        Display.create();

        GL11.glClearColor(0.4f, 0.6f, 0.9f, 1);
    }

    void init3D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        gluPerspective(90f, aspectRatio, 0.1f, 500f);

        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    void init2D() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(-aspectRatio, aspectRatio, 1, -1, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    void run() {
        running = true;

        try {
            initGame();
        } catch (LWJGLException e) {
            logger.log(Level.SEVERE, null, e);
        }

        Mouse.setGrabbed(true);

        currentWorld = new World();
        camera = new Camera();
        player = new Player(currentWorld);

        worldRenderer = new WorldRenderer();

        delta = 0;
        long lasttime = System.currentTimeMillis();

        while (running) {
            delta = (System.currentTimeMillis() - lasttime) / 1000f;
            lasttime = System.currentTimeMillis();

            input();
            logic();
            render();

            if (Display.isCloseRequested()) {
                running = false;
            }
        }
    }

    private void input() {
        player.input(delta);
    }

    private void logic() {
    }

    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

        init3D();

        camera.update();

        worldRenderer.render(currentWorld);

//        init2D();
//
//        GL11.glBegin(GL11.GL_TRIANGLES);
//        GL11.glVertex2f(0, 0);
//        GL11.glVertex2f(1, 1);
//        GL11.glVertex2f(1, 0);
//        GL11.glEnd();

        Display.update();
        Display.sync(60);
    }
}
