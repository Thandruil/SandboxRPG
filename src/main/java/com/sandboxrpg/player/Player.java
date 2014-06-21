package com.sandboxrpg.player;

import com.sandboxrpg.entities.Entity;
import com.sandboxrpg.entities.EntityPlayer;
import com.sandboxrpg.world.World;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Player {

    EntityPlayer entity;
    float speed = 30f;

    public Player(World world) {
        this.entity = new EntityPlayer(world);
    }

    public Entity getEntity() {
        return entity;
    }

    public void input(float delta) {
        float x = entity.getPosition().getX();
        float z = entity.getPosition().getZ();
        float roty = entity.getRotation().getY();
        float rotx = entity.getRotation().getX();

        if (Mouse.isGrabbed()) {
            float dx = Mouse.getDX() * 0.16f;
            float dy = Mouse.getDY() * 0.16f;

            if (roty + dx >= 360) {
                roty = roty + dx - 360;
            } else if (roty + dx < 0) {
                roty = 360 - roty + dx;
            } else {
                roty += dx;
            }

            if (rotx - dy >= -90 && rotx - dy <= 90) {
                rotx -= dy;
            } else if (rotx - dy < -90) {
                rotx = -90;
            } else if (rotx - dy > 90) {
                rotx = 90;
            }
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && entity.getVertical() == 0) {
            entity.setVertical(entity.getVertical() + 100);
        }

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            x -= speed * delta * Math.sin(Math.toRadians(-roty));
            z -= speed * delta * Math.cos(Math.toRadians(-roty));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            x += speed * delta * Math.sin(Math.toRadians(-roty));
            z += speed * delta * Math.cos(Math.toRadians(-roty));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            x += speed * delta * Math.sin(Math.toRadians(-roty - 90));
            z += speed * delta * Math.cos(Math.toRadians(-roty - 90));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            x += speed * delta * Math.sin(Math.toRadians(-roty + 90));
            z += speed * delta * Math.cos(Math.toRadians(-roty + 90));
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_3)) {
            float entityx = entity.getPosition().getX();
            float entityz = entity.getPosition().getZ();
            entity.getWorld().dig(entityx, entityz);
        }

        entity.setPosition(x, entity.getPosition().getY(), z);
        entity.setRotation(rotx, roty, 0);
    }
}
