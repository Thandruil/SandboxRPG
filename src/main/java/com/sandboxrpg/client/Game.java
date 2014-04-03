package com.sandboxrpg.client;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.lwjgl.opengl.GL11.*;

public class Game {

    private static final Logger logger = Logger.getLogger("");
    private static Game theGame;

    boolean running = true;

    int displayWidth;
    int displayHeight;
    float aspectRatio;
    boolean fullscreen;

    public Game(int width, int height, boolean fullscreen) {
        theGame = this;
        this.displayWidth = width;
        this.displayHeight = height;
        this.aspectRatio = width/height;
        this.fullscreen = fullscreen;
    }

    private void gameLoop() {
        glClear(GL_COLOR_BUFFER_BIT);
        Display.update();
        Display.sync(60);
    }

    private void initGame() throws LWJGLException {

        Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));

        Display.setResizable(false);
        Display.setTitle("SandboxRPG 0.0.1-SNAPSHOT");

        Display.create();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glMatrixMode(GL_MODELVIEW);
        glViewport(0, 0, this.displayWidth, this.displayHeight);
    }

    public void run() {
        this.running = true;

        try {
            initGame();
        } catch (LWJGLException e) {
            logger.log(Level.SEVERE, null, e);
        }

        while (running) {
            gameLoop();
        }
    }
}
