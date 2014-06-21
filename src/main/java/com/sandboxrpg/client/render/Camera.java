package com.sandboxrpg.client.render;

import com.sandboxrpg.entities.Entity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

public class Camera {

    private Vector3f position;
    private Vector3f rotation;

    public Camera() {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
    }

    public void lookThrough(Entity entity) {
        this.position = entity.getPosition();
        this.rotation = entity.getRotation();
    }

    public void update() {
        GL11.glRotatef(rotation.getX(), 1, 0, 0);
        GL11.glRotatef(rotation.getY(), 0, 1, 0);
        GL11.glTranslatef(-position.getX(), -position.getY(), -position.getZ());
    }
}
