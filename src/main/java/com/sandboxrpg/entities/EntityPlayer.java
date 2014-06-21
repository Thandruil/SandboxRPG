package com.sandboxrpg.entities;

import com.sandboxrpg.world.World;
import org.lwjgl.util.vector.Vector3f;

public class EntityPlayer implements EntityCreature {

    Vector3f position;
    Vector3f rotation;
    float vertical;
    World world;

    public EntityPlayer(World world) {
        this.position = new Vector3f();
        this.rotation = new Vector3f();
        this.vertical = 0;
        this.world = world;

        world.addEntity(this);
    }

    @Override
    public Vector3f getPosition() {
        return position;
    }

    @Override
    public Vector3f getRotation() {
        return rotation;
    }

    public float getVertical() {
        return vertical;
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void setPosition(float x, float y, float z) {
        position.set(x, y, z);
    }

    @Override
    public void setPosition(Vector3f position) {
        position.set(position);
    }

    @Override
    public void setRotation(float x, float y, float z) {
        rotation.set(x, y, z);
    }

    public void setVertical(float vertical) {
        this.vertical = vertical;
    }
}
