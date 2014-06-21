package com.sandboxrpg.entities;

import com.sandboxrpg.world.World;
import org.lwjgl.util.vector.Vector3f;

public interface Entity {
    Vector3f getPosition();

    Vector3f getRotation();

    float getVertical();

    World getWorld();

    void setPosition(float x, float y, float z);

    void setPosition(Vector3f position);

    void setRotation(float x, float y, float z);

    void setVertical(float vertical);
}
